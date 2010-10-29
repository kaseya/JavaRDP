/* RdesktopException.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:19 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: General exception class for ProperJavaRDP
 */
package com.elusiva.rdp;

public class RdesktopException extends Exception {

    public RdesktopException() {
	super();
    }

    public RdesktopException(String s) {
	super(s);
    }
}
