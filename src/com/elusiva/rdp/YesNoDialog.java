package com.elusiva.rdp;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Created by IntelliJ IDEA.
* User: Administrator
* Date: Sep 17, 2010
* Time: 11:22:38 AM
* To change this template use File | Settings | File Templates.
*/
class YesNoDialog extends Dialog implements ActionListener {

    Button yes, no;

    boolean retry = false;

    public YesNoDialog(RdesktopFrame parent,String title, String[] message) {
        super(parent, title, true);

        Panel msg = new Panel();
        msg.setLayout(new GridLayout(message.length, 1));
        for (int i = 0; i < message.length; i++)
            msg.add(new Label(message[i], Label.CENTER));
        this.add("Center", msg);

        Panel p = new Panel();
        p.setLayout(new FlowLayout());
        yes = createButton("Yes");
        p.add(yes);
        no = createButton("No");
        p.add(no);
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
        if (e.getSource() == yes)
            retry = true;
        else
            retry = false;
        this.hide();
        this.dispose();
    }
}
