/* Utilities.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:29 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Provide replacements for useful methods that were unavailable prior to
 *          Java 1.4 (Java 1.1 compliant).
 */

package com.elusiva.rdp;

import java.awt.datatransfer.DataFlavor;
import java.util.StringTokenizer;

public class Utilities {
    
    /**
     * Replaces each substring of this string that matches the given regular expression with the given replacement. 
     * @param in Input string
     * @param regex Regular expression describing patterns to match within input string
     * @param replace Patterns matching regular expression within input are replaced with this string
     * @return
     */
    public static String strReplaceAll(String in, String regex, String replace){
        String[] finArgs = null;
        StringTokenizer tok = new StringTokenizer(in, regex);
        for (Object[] obj = {tok,finArgs=new String[tok.countTokens()],new int[] {0}}; ((StringTokenizer)obj[0]).hasMoreTokens(); ((String[])obj[1])[((int[])obj[2])[0]++]=((StringTokenizer)obj[0]).nextToken()) {}
        String out = finArgs[0];
        for(int i = 1; i < finArgs.length; i++){
            out += replace + finArgs[i];
        }
        return out;
    }
    
    /**
     * Split a string into segments separated by a specified substring
     * @param in Input string
     * @param splitWith String with which to split input string
     * @return Array of separated string segments
     */
    public static String[] split(String in, String splitWith){
        String[] out = null;
        StringTokenizer tok = new StringTokenizer(in, splitWith);
        for (Object[] obj = {tok,out=new String[tok.countTokens()],new int[] {0}}; ((StringTokenizer)obj[0]).hasMoreTokens(); ((String[])obj[1])[((int[])obj[2])[0]++]=((StringTokenizer)obj[0]).nextToken()) {}
        return out;
    }
    
    public static DataFlavor imageFlavor = new DataFlavor(java.awt.Image.class,"image/x-java-image");
        
    
}
