/* RdesktopFrame_Localised.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:53 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.3 specific extension of RdesktopFrame class
 */
// Created on 07-Jul-2003

package com.elusiva.rdp;

public class RdesktopFrame_Localised extends RdesktopFrame {
	protected void checkFullScreenWidthHeight(){
		if(Options.fullscreen){
		//	can't remove frame decoration so reduce size to compensate
			Options.height -= 26;
			Options.width -= 8;
		}
	}
}


