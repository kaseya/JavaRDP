/* MemBltOrder.java
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

public class MemBltOrder extends ScreenBltOrder {

    private int color_table = 0;
    private int cache_id = 0;
    private int cache_idx = 0;

    public MemBltOrder() {
	super();
    }

    public int getColorTable(){
	return this.color_table;
    }

    public int getCacheID() {
	return this.cache_id;
    }

    public int getCacheIDX() {
	return this.cache_idx;
    }

    public void setColorTable(int color_table) {
	this.color_table = color_table;
    }

    public void setCacheID(int cache_id) {
	this.cache_id = cache_id;
    }

    public void setCacheIDX(int cache_idx) {
	this.cache_idx = cache_idx;
    }

    public void reset() {
	super.reset();
	color_table = 0;
	cache_id = 0;
	cache_idx = 0;
    }
}
