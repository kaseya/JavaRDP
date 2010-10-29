/* Brush.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:35 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: 
 */
package com.elusiva.rdp.orders;

public class Brush {

    private int xorigin = 0;
    private int yorigin = 0;
    private int style = 0;
    private byte[] pattern = new byte[8];

    public Brush() {
    }

    public int getXOrigin() {
	return this.xorigin;
    }

    public int getYOrigin() {
	return this.yorigin;
    }

    public int getStyle() {
	return this.style;
    }

    public byte[] getPattern(){
	return this.pattern;
    }

    public void setXOrigin(int xorigin) {
	this.xorigin = xorigin;
    }
    
    public void setYOrigin(int yorigin) {
	this.yorigin = yorigin;
    }

    public void setStyle(int style) {
	this.style = style;
    }

    public void setPattern(byte[] pattern){
	this.pattern = pattern;
    }

    public void reset() {
	xorigin = 0;
	yorigin = 0;
	style = 0;
	pattern = new byte[8];
    }
}
