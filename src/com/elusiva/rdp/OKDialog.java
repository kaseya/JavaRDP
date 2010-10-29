package com.elusiva.rdp;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
* User: Administrator
* Date: Sep 17, 2010
* Time: 11:23:00 AM
* To change this template use File | Settings | File Templates.
*/
class OKDialog extends Dialog implements ActionListener {

    public OKDialog(RdesktopFrame parent, String title, String[] message) {

        super(parent, title, true);

        Panel msg = new Panel();
        msg.setLayout(new GridLayout(message.length, 1));
        for (int i = 0; i < message.length; i++)
            msg.add(new Label(message[i], Label.CENTER));
        this.add("Center", msg);

        Button ok = createButton("OK");

        Panel p = new Panel();
        p.setLayout(new FlowLayout());

        p.add(ok);
        this.add("South", p);
        this.pack();

        if (getSize().width < 240)
            setSize(new Dimension(240, getSize().height));

        parent.centreWindow(this);
    }

    private Button createButton(String title) {
        Button button = new Button(title);
        button.addActionListener(this);
        return button;
    }
    public void actionPerformed(ActionEvent e) {
        this.hide();
        this.dispose();
    }
}
