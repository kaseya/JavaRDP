package com.elusiva.rdp;

import org.apache.log4j.Logger;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Sep 17, 2010
 * Time: 11:30:08 AM
 * To change this template use File | Settings | File Templates.
 */
class RdesktopWindowAdapter extends WindowAdapter {
    private RdesktopFrame rdesktopFrame;
    private RdesktopCanvas canvas;
    private Options option;
    static Logger logger = Logger.getLogger(RdesktopWindowAdapter.class);

    public RdesktopWindowAdapter(RdesktopFrame rdesktopFrame, RdesktopCanvas canvas, Options option) {
        this.rdesktopFrame = rdesktopFrame;
        this.canvas = canvas;
        this.option = option;
    }

    public void windowClosing(WindowEvent e) {
        rdesktopFrame.hide();
        Rdesktop.stopApplet(option.getDisconnectHandler());
        rdesktopFrame.disconnectPlayer();
    }

    public void windowLostFocus(WindowEvent e) {
        logger.info("windowLostFocus");
        // lost focus - need clear keys that are down
        canvas.lostFocus();
    }

    public void windowDeiconified(WindowEvent e) {
        if (Constants.OS == Constants.WINDOWS) {
            // canvas.repaint();
            canvas.repaint(0, 0, option.getWidth(), option.getHeight());
        }
        canvas.gainedFocus();
    }

    public void windowActivated(WindowEvent e) {
        if (Constants.OS == Constants.WINDOWS) {
            // canvas.repaint();
            canvas.repaint(0, 0, option.getWidth(), option.getHeight());
        }
        // gained focus..need to check state of locking keys
        canvas.gainedFocus();
    }

    public void windowGainedFocus(WindowEvent e) {
        if (Constants.OS == Constants.WINDOWS) {
            // canvas.repaint();
            canvas.repaint(0, 0, option.getWidth(), option.getHeight());
        }
        // gained focus..need to check state of locking keys
        canvas.gainedFocus();
    }
}
