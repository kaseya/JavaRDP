/* Constants.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:20 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Stores common constant values
 */
package com.elusiva.rdp;

public class Constants {
	
    public static final boolean desktop_save = true;
 //   public static final int keylayout = 0x809; // UK... was US, 0x409
    
    public static final boolean SystemExit = true;
    public static boolean encryption=true;
    public static boolean licence = true;
    
    public static final int WINDOWS = 1;
    public static final int LINUX = 2;
    public static final int MAC = 3;
    
    public static int OS = 0;

    public static boolean isBrowserRunningOnMac() {
        return OS == MAC;
    }

    public static boolean isBrowserRunningOnWindows() {
        return OS == WINDOWS;
    }

}
