/* RdesktopFrame_Localised.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:54 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.4 specific extension of RdesktopFrame class
 */
// Created on 07-Jul-2003

package com.elusiva.rdp;
import org.apache.log4j.Logger;

import java.awt.*;

public class RdesktopFrame_Localised extends RdesktopFrame {
    private static Logger logger = Logger.getLogger("com.elusiva.rdp.RdesktopFrame_Localised");

	public RdesktopFrame_Localised(){
		super();
	}

    public RdesktopFrame_Localised(RdesktopCanvas_Localised canvas, Options option){
		super(canvas, option);
	}
	protected void fullscreen(){
			setUndecorated(true);
			setExtendedState (Frame.MAXIMIZED_BOTH);
	}
	
	public void goFullScreen(){
		if(!option.isFullscreen()) return;
		
		inFullscreen = true;
		
		if(this.isDisplayable()) this.dispose();
		this.setVisible(false);
		this.setLocation(0, 0);
        logger.info("inset size" + Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration()));
               logger.info("size" + this.getSize());
               logger.info("screen size" + Toolkit.getDefaultToolkit().getScreenSize());



        // Determine if full-screen mode is supported directly
        //GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //GraphicsDevice gs = ge.getDefaultScreenDevice();

        //boolean isFullScreenSupported = gs.isFullScreenSupported();
        //logger.info("isFullScreenSupprted" + isFullScreenSupported);


//        if (isFullScreenSupported) {
//            if(this.isDisplayable()) this.dispose();
//            setUndecorated(isFullScreenSupported);
//            setResizable(!isFullScreenSupported);
//            // Full-screen mode
//            gs.setFullScreenWindow(this);
//            validate();
//            //pack();
//            setVisible(true);
//        } else {
            setUndecorated(true);
            setResizable(false);

            // Windowed mode
            pack();
            setVisible(true);
  //      }

        logger.debug("canvas size" + this.canvas.getSize());
        logger.debug("size" + this.getSize());
        logger.debug("screen size" + Toolkit.getDefaultToolkit().getScreenSize());


    }



    public void leaveFullScreen() {
		if(!option.isFullscreen()) return;
		inFullscreen = false;
        
		if(this.isDisplayable()) this.dispose();
		
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice myDevice = env.getDefaultScreenDevice();
		if (myDevice.isFullScreenSupported()) myDevice.setFullScreenWindow(null);
		
		this.setLocation(10, 10);
		this.setUndecorated(false);
		this.setVisible(true);
		//setExtendedState (Frame.NORMAL);
		this.pack();

	}
}
