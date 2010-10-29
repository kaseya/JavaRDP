/* RdpApplet.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:35 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Provide an applet interface to ProperJavaRDP
 */

package com.elusiva.rdp.applet;

import com.elusiva.rdp.*;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

public class RdpApplet extends Applet implements WindowListener {


    private TextArea aTextArea = null;
    private PrintStream aPrintStream = null;
    private  Container container;
    private GridBagLayout gridBag;


    public void paint(Graphics g) {
        g.setColor(new Color(0xFFFFFF));
        g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
        g.setColor(new Color(0x000000));
        int width = g.getFontMetrics().stringWidth("Launching session...");
        int x = (int) (g.getClipBounds().getWidth() / 2) - (width / 2);
        int y = (int) (g.getClipBounds().getHeight() / 2);
        // if(!redirectOutput) g.drawString("Launching Everywhere session...", x, y);
        width = g.getFontMetrics().stringWidth("Connect to:" + getParameter("server"));
        x = (int) (g.getClipBounds().getWidth() / 2) - (width / 2);
        y = (int) (g.getClipBounds().getHeight() / 2) + 20;
        //if(!redirectOutput) g.drawString("Connecting to:" + getParameter("server"), x, y);
    }

    boolean redirectOutput = false;

    public void init() {
        redirectOutput = isSet("redirectOutput");
        if (redirectOutput) {
            aPrintStream = new PrintStream(new FilteredStream(new ByteArrayOutputStream()));
            System.setOut(aPrintStream);
            System.setErr(aPrintStream);
            aTextArea = new TextArea();
            setLayout(new BorderLayout());
            add("Center", aTextArea);
        }
        this.gridBag = new GridBagLayout();
        this.container = null;
    }

    private volatile RdpThread rThread = null;

    public void start() {
        Common.underApplet = true;
        String argLine = "";
        argLine += genParam("-m", "keymap");
        argLine += " " + genParam("-u", "username");
        argLine += " " + genParam("-p", "password");
        argLine += " " + genParam("-n", "hostname");
        argLine += " " + genParam("-t", "port");
        argLine += " " + genParam("-l", "debug_level");
        argLine += " " + genParam("-s", "shell");
        argLine += " " + genParam("-T", "title");
        argLine += " " + genParam("-c", "command");
        argLine += " " + genParam("-d", "domain");
        argLine += " " + genParam("-o", "bpp");
        argLine += " " + genParam("-g", "geometry");
        argLine += " " + genParam("-s", "shell");
        argLine += " " + genFlag("--console", "console");
        argLine += " " + genFlag("--use_rdp4", "rdp4");
        argLine += " " + genFlag("--debug_key", "debug_key");
        argLine += " " + genFlag("--debug_hex", "debug_hex");
        argLine += " " + genFlag("--no_remap_hash", "no_remap_hash");
        argLine += " " + genFlag("--no_remap_hash", "no_remap_hash");
        argLine += " " + genFlag("--enable_menu", "disable_menu");
        argLine += " " + genFlag("--embed_in_browser", "embed_in_browser");
        argLine += " " + genParam("--error_handler", "error_handler");
        argLine += " " + genParam("--disconnect_handler", "disconnect_handler");
        argLine += " " + genParam("-f", "fullscreen");
        argLine += " " + genParam("", "server");

        String[] args;
        StringTokenizer tok = new StringTokenizer(argLine, " ");
        for (Object[] obj = {tok, args = new String[tok.countTokens()], new int[]{0}}; ((StringTokenizer) obj[0]).hasMoreTokens(); ((String[]) obj[1])[((int[]) obj[2])[0]++] = ((StringTokenizer) obj[0]).nextToken()) {
        }
       rThread = new RdpThread(args, this.getParameter("redirect_on_exit"), this);
       rThread.setName("Applet Thread");
       System.out.print("Starting rThread**********" + rThread.getId() + "\n");
       rThread.start();
    }

        
    public void stop() {
        System.out.print("Stopping Applet*********\n");
        cleanup();
    }

    public boolean isStopped() {
        return rThread == null;
    }

    public void sendCtrlAltDel() {
        Component canvas = getComponent(0);
        if (canvas != null && canvas instanceof RdesktopCanvas) {
            ((RdesktopCanvas) canvas).sendCtrlAltDel();
        }
    }

    public void setContainer(Container container) {
        if ( container instanceof Window) {
            this.container = container;
        }
    }

    private void cleanup() {
        System.out.print("Inside Cleaning");
        if (rThread != null) {
            rThread.disconnect();
            rThread = null;
        }
        if ( container == null && getComponentCount() > 0) {
            this.remove(0);
        } else {
            container = null;
        }
        gridBag = null;
    }

    private boolean isSet(String parameter) {
        String s = this.getParameter(parameter);
        if (s != null) {
            if (s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("true"))
                return true;
        }
        return false;
    }


    private String genFlag(String flag, String parameter) {
        String s = this.getParameter(parameter);
        if (s != null) {
            if (s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("true"))
                return flag;
        }
        return "";
    }

    private String genParam(String name, String parameter) {
        String s = this.getParameter(parameter);
        if (s != null) {
            if (name != "")
                return name + " " + s;
            else return s;
        } else
            return "";
    }

    // Overload that passes arguments to the JavaScript event handler
    public void invokeJavaScriptEventHandler(String eventHandler, String[] argumentList) {
        System.out.println("Event Handler" + eventHandler);
        try {
            if (eventHandler != null && !eventHandler.isEmpty()) {
                StringBuilder argumentString = new StringBuilder();
                if (argumentList != null && argumentList.length > 0) {
                    argumentString.append(argumentList[0]);
                    for (int i = 1; i < argumentList.length; ++i) {
                        argumentString.append(",");
                        argumentString.append(argumentList[i]);
                    }
                }
                System.out.println("Invoking java script" + eventHandler + "Argument String" + argumentString.toString());
                // Invoke JavaScript
                getAppletContext().showDocument
                        (new URL("javascript:" + eventHandler + "(\"" + argumentString.toString() + "\");"), "_self");
            }
        }
        catch (MalformedURLException malformedException) {
            // Ignore failure
        }
    }

    class FilteredStream extends FilterOutputStream {
        public FilteredStream(OutputStream aStream) {
            super(aStream);
        }

        public void write(byte b[]) throws IOException {
            String aString = new String(b);
            aTextArea.append(aString);
        }

        public void write(byte b[], int off, int len) throws IOException {
            String aString = new String(b, off, len);
            aTextArea.append(aString);
        }
    }

    public void windowOpened(WindowEvent e) {}
    public void windowClosing(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}


}

class RdpThread extends Thread {

    private String[] args;
    private String redirect = null;
    private RdpApplet parentApplet;

    public RdpThread(String[] args, String redirect, RdpApplet a) {
        this.parentApplet = a;
        this.args = args;
        this.redirect = redirect;
    }

    public void run() {
        if (parentApplet != null && parentApplet.isStopped()) {
            return;
        }
        this.setPriority(Thread.MAX_PRIORITY);

            try {
                Rdesktop.main(args, parentApplet);
                System.out.print("Rdesktop main finished");
                if (redirect != null) {
                    URL u = new URL(redirect);
                    parentApplet.getAppletContext().showDocument(u);
                }
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                e.printStackTrace();
            }
            Common.underApplet = false;
            System.out.print("Stopping applet thread" + this.getId());
    }


    public void disconnect() {
        if ( Common.isRunningAsApplet()) {
           System.out.print("Stopping Desktop" + this.getId());
            Rdesktop.stopRdesktop();
        } else {
            System.out.print("Desktop already stopped" + this.getId());
        }
    }
}

