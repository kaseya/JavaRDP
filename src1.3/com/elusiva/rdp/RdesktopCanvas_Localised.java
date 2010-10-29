/* RdesktopCanvas_Localised.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:53 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.3 specific extension of RdesktopCanvas class
 */
// Created on 03-Sep-2003

package com.elusiva.rdp;
import java.awt.image.*;
import java.awt.*;

import com.elusiva.rdp.Options;


public class RdesktopCanvas_Localised extends RdesktopCanvas {
	
	private Robot robot = null;

RdesktopCanvas_Localised(int width, int height){
	super(width,height);
}

	public void movePointer(int x, int y){
		Point p = this.getLocationOnScreen();
		x = x + p.x;
		y = y + p.y;
		robot.mouseMove(x, y);
	}
	
	protected Cursor createCustomCursor(Image wincursor, Point p, String s, int cache_idx){
		return Toolkit.getDefaultToolkit().createCustomCursor(wincursor, p, "");
	}	
		
		public void addNotify(){
		super.addNotify();

		if (robot == null) {
			try {
				robot = new Robot();
			} catch(AWTException e) {
			logger.warn("Pointer movement not allowed");
			}
		}
	}
	
	public void update(Graphics g){
		Rectangle r = g.getClipBounds();
        g.drawImage(backstore.getSubimage(r.x,r.y,r.width,r.height),r.x,r.y,null);
	}
}
