/* Common.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:14 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Provide a static interface to individual network layers
 *          and UI components
 */
package com.elusiva.rdp;

import com.elusiva.rdp.rdp5.Rdp5;
import com.elusiva.rdp.applet.RdpApplet;

public class Common {

    public static boolean underApplet = false;
	public static Rdp5 rdp;
	//public static Secure secure;
    public static RdesktopFrame frame;
    public static RdesktopCanvas_Localised canvas;


    public static boolean isRunningAsApplication() {
        return !underApplet;
    }

    public static boolean isRunningAsApplet() {
        return underApplet;
    }
}
