/* RdesktopCanvas_Localised.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:59 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.1 specific extension of RdesktopCanvas class
 */
// Created on 03-Sep-2003

package com.elusiva.rdp;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.*;
import java.awt.*;

import com.elusiva.rdp.Options;

public class RdesktopCanvas_Localised extends RdesktopCanvas {
	RdesktopCanvas_Localised(int width, int height){
			super(width,height);
		}
	
		public void update(Graphics g) {
            Rectangle r = g.getClipBounds();
            g.drawImage(backstore.getSubimage(r.x,r.y,r.width,r.height),r.x,r.y,null);
		}
}
