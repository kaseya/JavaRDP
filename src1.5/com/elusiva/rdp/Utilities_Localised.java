/* Utilities_Localised.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:54 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.4 specific extension of Utilities class
 */
package com.elusiva.rdp;

import java.awt.datatransfer.DataFlavor;
import java.util.StringTokenizer;

import com.elusiva.rdp.Utilities;

public class Utilities_Localised extends Utilities {

    public static DataFlavor imageFlavor = DataFlavor.imageFlavor;
    
    public static String strReplaceAll(String in, String find, String replace){
        return in.replaceAll(find, replace);
    }
    
    public static String[] split(String in, String splitWith){
        return in.split(splitWith);
    }
    
}
