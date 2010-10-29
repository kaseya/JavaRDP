package com.elusiva.rdp;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Created by IntelliJ IDEA.
* User: Administrator
* Date: Sep 17, 2010
* Time: 11:30:26 AM
* To change this template use File | Settings | File Templates.
*/
class RdesktopComponentAdapter extends ComponentAdapter {
    private RdesktopCanvas canvas;
    private Options option;

    public RdesktopComponentAdapter(RdesktopCanvas canvas, Options option) {
        this.canvas = canvas;
        this.option = option;
    }

    public void componentMoved(ComponentEvent e) {
        canvas.repaint(0, 0, option.getWidth(), option.getHeight());
    }
}
