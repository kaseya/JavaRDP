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
import java.awt.*;

public class RdesktopFrame_Localised extends RdesktopFrame {
	public RdesktopFrame_Localised(){
		super();
	}
	protected void fullscreen(){
			setUndecorated (true);
			setExtendedState (Frame.MAXIMIZED_BOTH);
	}
	
	public void goFullScreen(){
		if(!Options.fullscreen) return;
		
		inFullscreen = true;
		
		if(this.isDisplayable()) this.dispose();
		this.setVisible(false);
		this.setLocation(0, 0);
		this.setUndecorated(true);
		this.setVisible(true);
		//setExtendedState (Frame.MAXIMIZED_BOTH);
		//GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		//GraphicsDevice myDevice = env.getDefaultScreenDevice();
		//if (myDevice.isFullScreenSupported()) myDevice.setFullScreenWindow(this);
		
		this.pack();
	}
	
	public void leaveFullScreen() {
		if(!Options.fullscreen) return;
		
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
