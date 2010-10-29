package com.elusiva.rdp;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

/**
 * Created by IntelliJ IDEA.
* User: Administrator
* Date: Sep 17, 2010
* Time: 11:29:52 AM
* To change this template use File | Settings | File Templates.
*/
public class RdesktopFocusListener implements FocusListener {
    private RdesktopCanvas canvas;
    private Options option;

    public RdesktopFocusListener(RdesktopCanvas canvas, Options option) {
        this.canvas = canvas;
        this.option = option;
    }

    public void focusGained(FocusEvent arg0) {
        if (Constants.OS == Constants.WINDOWS) {
            // canvas.repaint();
            canvas.repaint(0, 0, option.getWidth(), option.getHeight());
        }
        // gained focus..need to check state of locking keys
        canvas.gainedFocus();
    }

    public void focusLost(FocusEvent arg0) {
        //  lost focus - need clear keys that are down
        canvas.lostFocus();
    }
}
