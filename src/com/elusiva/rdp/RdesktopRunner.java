package com.elusiva.rdp;

import com.elusiva.rdp.applet.RdpApplet;
import com.elusiva.rdp.rdp5.Rdp5;
import com.elusiva.rdp.tools.SendEvent;

import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.SocketException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Oct 7, 2010
 * Time: 10:34:13 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class RdesktopRunner {
    protected Options option;
    private static Logger logger = Logger.getLogger("com.elusiva.rdp.RdesktopRunner");
    protected RdpApplet applet;
    private static final String[] LICENSE_ISSUE_MSG = new String[]{"The terminal server disconnected before licence negotiation completed.",
                    "Possible cause: terminal server could not issue a licence."
                    };
    private Rdp5 rdpLayer;

    protected RdesktopRunner(Options option, RdpApplet parentApplet) {
        this.option = option;
        applet = parentApplet;
    }

    abstract void reportError(String[] errorMessages);
    abstract void handleRdesktopException(RdesktopException e, Rdp5 rdpLayer);
    abstract void registerDrawingSurface(Rdp5 rdpLayer);

    void disconnectRdpPlayer() {
        if (rdpLayer != null && rdpLayer.isConnected()) {
            logger.info("Disconnecting ...");
            rdpLayer.disconnect();
            logger.info("Disconnected");
        }
    }

    SendEvent displayToolsFrame(boolean shouldShowToolsFrame) {
        SendEvent toolFrame = null;
        if (shouldShowToolsFrame) {
            toolFrame = new SendEvent(rdpLayer);
            toolFrame.setVisible(true);
        }
        return toolFrame;
    }

    void removeToolsFrame(SendEvent toolsFrame) {
        if (toolsFrame != null)
            toolsFrame.dispose();
        toolsFrame = null;
    }



    void runRdp(String server) {
        boolean[] deactivated = new boolean[1];
        int[] ext_disc_reason = new int[1];

        // Attempt to connect to server on port Options.port
        try {
            int logonflags = option.getLogonFlags();
            rdpLayer.connect(InetAddress.getByName(server), logonflags);
            SendEvent toolsFrame = displayToolsFrame(option.shouldShowToolsFrame());

            if (Rdesktop.keep_running) {
                option.disableEncryptionIfPacketEncyptionNotEnabled();

                logger.info("Connection successful");
                // now show window after licence negotiation
                rdpLayer.mainLoop(deactivated, ext_disc_reason);
                if (deactivated[0] ||
                        ext_disc_reason[0] == DisconnnectCodeMapper.exDiscReasonAPIInitiatedDisconnect ||
                        ext_disc_reason[0] == DisconnnectCodeMapper.exDiscReasonAPIInitiatedLogoff) {
                    Rdesktop.keep_running = false;
                    return;
                }

                if (ext_disc_reason[0] >= 2) {
                    String reason = DisconnnectCodeMapper.textDisconnectReason(ext_disc_reason[0]);
                    String msg[] = {"Connection terminated", reason};
                    reportError(msg);
                    Rdesktop.keep_running = false;
                    logger.warn("Connection terminated: " + reason);
                    return;
                }
                Rdesktop.keep_running = false; // exited main loop
                if (rdpLayer.isNotReadyToSendData()) {
                    // maybe the licence server was having a comms
                    // problem, retry?
                    logger.warn(LICENSE_ISSUE_MSG[0] + " " + LICENSE_ISSUE_MSG[1]);
                    reportError(LICENSE_ISSUE_MSG);
                }
            }
            removeToolsFrame(toolsFrame);
        } catch (ConnectionException e) {
            String msg[] = {"Connection Exception", e.getMessage()};
            reportError(msg);
            Rdesktop.keep_running = false;
        } catch (UnknownHostException e) {
            String[] msg = {e.getClass().getName(), e.getMessage()};
            reportError(msg);
            Rdesktop.keep_running = false;
        } catch (SocketException s) {
            if (rdpLayer.isConnected()) {
                String[] msg = {s.getClass().getName(), s.getMessage()};
                reportError(msg);
                Rdesktop.keep_running = false;
            }
        } catch (RdesktopException e) {
            handleRdesktopException(e, rdpLayer);
        } catch (Exception e) {
            String[] msg = {e.getClass().getName(), e.getMessage()};
            reportError(msg);
            Rdesktop.keep_running = false;
        }
    }

    public void run(String server, Secure secureLayer) {
        logger.debug("keep_running = " + Rdesktop.keep_running);
        rdpLayer = null;
        while (Rdesktop.keep_running) {
            logger.debug("Initialising RDP layer...");
            rdpLayer = new Rdp5(option, secureLayer);
            if (rdpLayer == null) {
                logger.fatal("The communications layer could not be initiated!");
                break;
            }
            registerDrawingSurface(rdpLayer);
            logger.info("Registered comms layer...");
            logger.info("Connecting to " + server + ":" + option.getPort() + " ...");
            runRdp(server);
        }
        disconnectRdpPlayer();
    }

    public static class RdesktopAppletRunner extends RdesktopRunner {
        RdesktopCanvas_Localised canvas;

        public RdesktopAppletRunner(RdpApplet parentApplet, Options option, RdesktopCanvas_Localised canvas) {
            super(option, parentApplet);
            initApplet(canvas);
        }

        private void initApplet(RdesktopCanvas_Localised canvas) {
            if (Constants.OS == Constants.WINDOWS) {
                // redraws screen on window move
                this.applet.addComponentListener(new RdesktopComponentAdapter(canvas, this.option));
            }
            this.canvas = canvas;
            this.applet.add(canvas);
            this.applet.validate();
            logger.info("Applet Size:" + this.applet.getSize());
            logger.info("Canvas Size:" + this.applet.getComponent(0).getSize());
        }

        void reportError(String[] errorMessages) {
            if (option.isErrorHandlerAvailable() && Common.isRunningAsApplet()) {
                applet.invokeJavaScriptEventHandler(option.getErrorHandler(), errorMessages);
            }
        }

        void handleRdesktopException(RdesktopException e, Rdp5 rdpLayer) {
            if (rdpLayer.isReadyToSendData()) {
                String msg[] = {e.getMessage()};
                reportError(msg);
                Rdesktop.keep_running = false;
                return;
            }
            logger.info("Do not retry.");
            Rdesktop.keep_running = false;
            return;
        }

        void registerDrawingSurface(Rdp5 rdpLayer) {
            rdpLayer.registerDrawingSurface(canvas);
        }
    }

    public static class RdesktopFrameRunner extends RdesktopRunner {
        private RdesktopFrame window;
        private static final String[] TERMINAL_SERVER_RETRY_MSG = new String[]{
                    "The terminal server reset connection before licence negotiation completed.",
                "Possible cause: terminal server could not connect to licence server.",
                "Retry?"};

        public RdesktopFrameRunner(RdpApplet parentApplet, Options option, RdesktopFrame window) {
            super(option, parentApplet);
            this.window = window;
            if (Common.isRunningAsApplet()) {
                this.applet.setContainer(window);
            }
        }

        void reportError(String[] errorMessages) {
            if (option.isErrorHandlerAvailable() && Common.isRunningAsApplet()) {
                applet.invokeJavaScriptEventHandler(option.getErrorHandler(), errorMessages);
            } else {
                window.showErrorDialog(errorMessages);
            }
        }

        void handleRdesktopException(RdesktopException e, Rdp5 rdpLayer) {
            if (rdpLayer.isReadyToSendData()) {
                String msg[] = {e.getMessage()};
                reportError(msg);
                Rdesktop.keep_running = false;
                return;
            }
            // maybe the licence server was having a comms
            // problem, retry?
            boolean retry = window.showYesNoErrorDialog(TERMINAL_SERVER_RETRY_MSG);

            if (!retry) {
                logger.info("Do not retry.");
                Rdesktop.keep_running = false;
                return;
            }
            disconnectRdpPlayer();
            logger.info("Retrying connection...");
        }

        void registerDrawingSurface(Rdp5 rdpLayer) {
            rdpLayer.registerDrawingSurface(window);
        }
    }
}
