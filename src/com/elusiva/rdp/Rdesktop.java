/* Rdesktop.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:22 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Main class, launches session
 */
package com.elusiva.rdp;

import com.elusiva.rdp.applet.RdpApplet;
import com.elusiva.rdp.keymapping.KeyCode_FileBased;
import com.elusiva.rdp.keymapping.KeyMapException;
import com.elusiva.rdp.rdp5.VChannels;
import com.elusiva.rdp.rdp5.cliprdr.ClipChannel;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Rdesktop {

    private static Logger logger = Logger.getLogger("com.elusiva.rdp");

    static boolean keep_running;

    private static final String KEY_MAP_PATH = "keymaps/";

    static String PROGRAM_NAME = "Elusiva Everywhere";
    static RdpApplet parentApplet = null;
    static RdesktopRunner runner;


    static {
        BasicConfigurator.configure();
    }

    /**
     * Outputs version and usage information via System.err
     */
    public static void usage() {
        System.err.println("Version " + Version.version);
        System.err.println("Usage: java com.elusiva.rdp.Rdesktop [options] server[:port]");
        System.err.println("	-b 							 bandwidth saving (good for 56k modem, but higher latency");
        System.err.println("	-c DIR						 working directory");
        System.err.println("	-d DOMAIN					 logon domain");
        System.err.println("	-f[l]						 full-screen mode [with Linux KDE optimization]");
        System.err.println("	-g WxH						 desktop geometry");
        System.err.println("	-m MAPFILE					 keyboard mapping file for terminal server");
        System.err.println("	-l LEVEL					 logging level {DEBUG, INFO, WARN, ERROR, FATAL}");
        System.err.println("	-n HOSTNAME					 client hostname");
        System.err.println("	-p PASSWORD					 password");
        System.err.println("	-s SHELL					 shell");
        System.err.println("	-t NUM						 RDP port (default 3389)");
        System.err.println("	-T TITLE					 window title");
        System.err.println("	-u USERNAME					 user name");
        System.err.println("	-o BPP						 bits-per-pixel for display");
        System.err.println("    -r path                      path to load licence from (requests and saves licence from server if not found)");
        System.err.println("    --save_licence               request and save licence from server");
        System.err.println("    --load_licence               load licence from file");
        System.err.println("    --console                    connect to console");
        System.err.println("	--debug_key 				 show scancodes sent for each keypress etc");
        System.err.println("	--debug_hex 				 show bytes sent and received");
        System.err.println("	--no_remap_hash 			 disable hash remapping");
        System.err.println("	--quiet_alt 				 enable quiet alt fix");
        System.err.println("	--no_encryption				 disable encryption from client to server");
        System.err.println("	--use_rdp4					 use RDP version 4");
        //System.err.println("    --enable_menu               enable menu bar");
        System.err.println("	--log4j_config=FILE			 use FILE for log4j configuration");
        //System.err.println("    --error_handler=errorHandler javscript handler for error handling");
        //System.err.println("    --disconnect_handler=disconnectHandler,javscript handler for error handling");
        System.err.println("Example: java com.elusiva.rdp.Rdesktop -g 800x600 -l WARN m52.propero.int");
        exitIfApplication(0, true);
    }


    /**
     * @param args
     */
    public static void main(String[] args) {
        main(args, null);
    }


    static boolean isSuccessRunningNativeRDPClient(String[] args) {
        RDPClientChooser Chooser = new RDPClientChooser();
        Options option = new Options();
        return Chooser.RunNativeRDPClient(args, null);
    }

   static VChannels initializeChannels(ClipChannel clipChannel, Options option) throws RdesktopException{
        VChannels channels = new VChannels();
        // Initialise all RDP5 channels
        if (option.shouldUseRdp5() && option.isClipboardMappingEnabled()) {
                channels.register(clipChannel, option);

        }
        return channels;
    }


    static KeyCode_FileBased loadKeyMap(Options option) throws KeyMapException, IOException {
        KeyCode_FileBased keyMap = null;
        logger.info("looking for: " + "/" + KEY_MAP_PATH + option.getMapFile());
        InputStream istr = Rdesktop.class.getResourceAsStream("/" + KEY_MAP_PATH + option.getMapFile());
        // logger.info("istr = " + istr);
        if (istr == null) {
            logger.debug("Loading keymap from filename");
            keyMap = new KeyCode_FileBased_Localised(KEY_MAP_PATH + option.getMapFile(), option);
        } else {
            logger.debug("Loading keymap from InputStream");
            keyMap = new KeyCode_FileBased_Localised(istr, option);
        }
        if (istr != null) istr.close();
        return keyMap;
    }

    static RdesktopCanvas_Localised createCanvas(Options option, RdpApplet parentApplet) {
        option.setWidth(option.isEmbededInBrowser() ? parentApplet.getWidth() : option.getWidth());
        option.setHeight(option.isEmbededInBrowser() ? parentApplet.getHeight() : option.getHeight());
        logger.info("Option Height" + option.getHeight() + "Option width" + option.getWidth());
        RdesktopCanvas_Localised canvas = new RdesktopCanvas_Localised(
                option.getWidth(), option.getHeight(), option);

        return canvas;
    }

    /**
     * Disconnects from the server connected to through rdp and destroys the
     * RdesktopFrame window.
     * <p/>
     * Exits the application iff sysexit == true, providing return value n to
     * the operating system.
     *
     * @param n
     * @param sysexit
     * @param window
     * @param option
     */
    public static synchronized void exit(int n, boolean sysexit, RdesktopFrame window, Options option) {
        closeDesktopFrame(window, option);
        if (sysexit) {
            Common.rdp = null;
            //Common.frame = null;
            //find a way to destory below objects.
            Common.canvas = null;
            parentApplet = null;
        }

        exitIfApplication(n, sysexit);
        return;
    }

    public static synchronized  void stopApplet(String disconnectHandler) {
        keep_running = false;
        logger.info("Stop Applet");
       
        if ( disconnectHandler != null && ! disconnectHandler.isEmpty()) {
            parentApplet.invokeJavaScriptEventHandler(disconnectHandler, null);
        }

    }

    private static void exitIfApplication(int n, boolean sysexit) {
        logger.info("isRunningUnderApplet:" + Common.underApplet + " Exiting");
        if (sysexit && Common.isRunningAsApplication()) {
                logger.info("Exiting Application");
                System.exit(n);
        }
        return;
    }

    private static void closeDesktopFrame(RdesktopFrame window, Options option) {
        if ( Common.isRunningAsApplet()) {
            stopApplet(option.getDisconnectHandler());
        }
        if (window != null) {
            window.setVisible(false);
            window.removeAll();
            window.dispose();
        }
    }


    //Not sure, why we need this ????
    public static void error(Exception e, RdesktopFrame window, boolean sysexit, Options option) {
        logger.fatal(e.getMessage());
        String[] msg = {e.getClass().getName(), e.getMessage()};
        reportError(window, msg, option);
        keep_running = false;
        closeDesktopFrame(window, option);
        if (sysexit) {
            Common.rdp = null;
            //Common.frame = null;
            //find a way to destory below objects.
            //Common.canvas = null;
            parentApplet = null;
        }
        exitIfApplication(0, sysexit);
    }

    static void reportError(RdesktopFrame window, String[] errorMessages, Options option) {
        if (option.isErrorHandlerAvailable() && Common.isRunningAsApplet()) {
            parentApplet.invokeJavaScriptEventHandler(option.getErrorHandler(), errorMessages);
        } else {
            if (window != null) {
                window.showErrorDialog(errorMessages);

            }
        }
    }

    public static void stopRdesktop() {
        keep_running = false;
        runner.disconnectRdpPlayer();
    }

    /**
     * @param args
     * @param parentApplet
     */
    public static void main(String[] args, RdpApplet parentApplet) {

        if (args.length == 0)
            usage();

        if (isSuccessRunningNativeRDPClient(args)) {
            if (!Common.underApplet) System.exit(0);
        }

        keep_running = true;
        logger.setLevel(Level.INFO);

        OptionsBuilder parser = new OptionsBuilder(args);
        Options option = parser.populate();
        String server = parser.parseServerName(option);
        logger.info("Version " + Version.version + "embed" + option.isEmbededInBrowser());

        RdesktopFrame window = null;
        Rdesktop.parentApplet = parentApplet;
        RdesktopCanvas_Localised canvas = createCanvas(option, parentApplet);


        if ( ! ( option.isEmbededInBrowser() && option.isNotFullscreen()) ) {
            window = new RdesktopFrame_Localised(canvas, option);
        }

        // Configure a keyboard layout
        KeyCode_FileBased keyMap = null;
        try {
            keyMap = loadKeyMap(option);
        } catch (Exception kmEx) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            kmEx.printStackTrace(pw);
            reportError(window, new String[]{sw.toString()}, option);
            exitIfApplication(0, true);
            return;
        }

        VChannels channels;
        ClipChannel clipChannel = new ClipChannel(option);

        try {
            channels = initializeChannels(clipChannel, option);
        } catch( RdesktopException e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            reportError(window, new String[]{sw.toString()}, option);
            exitIfApplication(0, true);
            return;
        }

        Secure secureLayer = new Secure(channels,option);

        canvas.addFocusListener(clipChannel);
        canvas.addFocusListener(new RdesktopFocusListener(canvas, option));
        canvas.registerKeyboard(keyMap);

        logger.debug("Registering keyboard...");
        option.setKeylayout(keyMap.getMapCode());

        if (option.isEmbededInBrowser() && option.isNotFullscreen()) {
            runner = new RdesktopRunner.RdesktopAppletRunner(parentApplet,option, canvas);
            runner.run(server, secureLayer);
        } else {
            runner = new RdesktopRunner.RdesktopFrameRunner(parentApplet,option,window);
            runner.run(server, secureLayer);
        }

        logger.info("Exit main");

        exit(0, true, window, option);
    }

}
