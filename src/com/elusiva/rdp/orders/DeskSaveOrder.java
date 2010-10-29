/* DeskSaveOrder.java
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

public class DeskSaveOrder extends BoundsOrder {

    private int offset = 0;
    private int action = 0;
    
    public DeskSaveOrder() {
	super();
    }

    public int getOffset() {
	return this.offset;
    }

    public int getAction() {
	return this.action;
    }

    public void setOffset(int offset) {
	this.offset = offset;
    }

    public void setAction(int action) {
	this.action = action;
    }
    
    public void reset() {
	super.reset();
	offset = 0;
	action = 0;
    }
}
