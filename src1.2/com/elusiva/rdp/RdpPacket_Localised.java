/* RdpPacket_Localised.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:44 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.2 specific extension of RdpPacket class
 */
package com.elusiva.rdp;

public class RdpPacket_Localised extends RdpPacket {
	private byte[] bb = null;
	
	private int size=-1;
   	private int position = -1;
    
	public RdpPacket_Localised(int capacity) {
	bb = new byte[capacity];
	size = capacity;
	position = 0;
	}
    
    public int capacity(){
        return size;
    }
       
	public void set8(int where, int what) {
	if (where < 0 || where >= size) { 
		throw new ArrayIndexOutOfBoundsException("memory accessed out of Range!"); 
	}
	bb[where] = (byte)what;
	}

	public void set8(int what) {
	if (position >= size) { 
		throw new ArrayIndexOutOfBoundsException("memory accessed out of Range!"); 
	}
	bb[position++] = (byte)what;
	}
   
	// where is a 8-bit offset
	public int get8(int where) {
	if (where < 0 || where >= size) { 
		throw new ArrayIndexOutOfBoundsException("memory accessed out of Range!"); 
	}
	return bb[where]&0xff; // treat as unsigned byte
	}

	// where is a 8-bit offset
	public int get8() {
	if (position >= size) { 
		throw new ArrayIndexOutOfBoundsException("memory accessed out of Range!"); 
	}
	return bb[position++]&0xff; // treat as unsigned byte
	}

	public void copyFromByteArray(byte[] array, int array_offset, int mem_offset, int len){
	if ((array_offset >= array.length) || (array_offset + len > array.length) || (mem_offset + len > size)){
		throw new ArrayIndexOutOfBoundsException("memory accessed out of Range!");
	}
	
	System.arraycopy(array, array_offset, bb, mem_offset, len);
	}
    
	public void copyToByteArray(byte[] array, int array_offset, int mem_offset, int len) {
	if ((array_offset >= array.length) || (array_offset + len > array.length) ||  (mem_offset + len > size)) {
		throw new ArrayIndexOutOfBoundsException("memory accessed out of Range!");
	}
	
	System.arraycopy(bb, mem_offset, array, array_offset, len);
	}

	public void copyToPacket(RdpPacket_Localised dst, int srcOffset, int dstOffset, int len) {
	System.arraycopy(bb,srcOffset,dst.bb,dstOffset,len);
	}

   public void copyFromPacket(RdpPacket_Localised src, int srcOffset, int dstOffset, int len) {
	System.arraycopy(src.bb,srcOffset,bb,dstOffset,len);
   }
   
	// return size in bytes
	public int size() {
	return size;
	}
   
	public int getPosition() {
	return position;
	}

	public int getLittleEndian16(int where) { 
	return (bb[where]&0xff)+((bb[where+1]&0xff)<<8);
	 }

	public int getLittleEndian16() { 
	return (bb[position++]&0xff)+((bb[position++]&0xff)<<8);
	 }

	public int getBigEndian16(int where) { 
	return ((bb[where]&0xff)<<8)+(bb[where+1]&0xff);
	}
    
	public int getBigEndian16() { 
		return ((bb[position++]&0xff)<<8)+(bb[position++]&0xff);
	}
    
	public void setLittleEndian16(int where, int what)  {
		bb[where] 		= (byte)(what&0xff);
		bb[where+1] 	= (byte)((what>>8)&0xff);
	}
    
	public void setLittleEndian16(int what)  {
		bb[position++] = (byte)(what&0xff);
		bb[position++] = (byte)((what>>8)&0xff);
	}
    
	public void setBigEndian16(int where, int what)  {
		bb[where] 		= (byte)((what>>8)&0xff);
		bb[where+1] 	= (byte)(what&0xff);
	}
    
	public void setBigEndian16(int what)  {
		bb[position++] = (byte)((what>>8)&0xff);
		bb[position++] = (byte)(what&0xff);
	}

	public int getLittleEndian32(int where) { 
		return (bb[where]&0xff)+((bb[where+1]&0xff)<<8)+((bb[where+2]&0xff)<<16)+((bb[where+3]&0xff)<<24);
	}

	public int getLittleEndian32() { 
		return (bb[position++]&0xff)+((bb[position++]&0xff)<<8)+((bb[position++]&0xff)<<16)+((bb[position++]&0xff)<<24);
	}

	 public int getBigEndian32(int where) { 
		return ((bb[where]&0xff)<<24)+((bb[where+1]&0xff)<<16)+((bb[where+2]&0xff)<<8)+(bb[where+3]&0xff);
	}

	 public int getBigEndian32() { 
		return ((bb[position++]&0xff)<<24)+((bb[position++]&0xff)<<16)+((bb[position++]&0xff)<<8)+(bb[position++]&0xff);
	 }
	
	public void setLittleEndian32(int where, int what)  {
		bb[where] 		= (byte)(what&0xff);
		bb[where+1] 	= (byte)((what>>8)&0xff);
		bb[where+2]	= (byte)((what>>16)&0xff);
		bb[where+3]	= (byte)((what>>24)&0xff);
	}

	public void setLittleEndian32(int what)  {
		bb[position++] = (byte)(what&0xff);
		bb[position++] = (byte)((what>>8)&0xff);
		bb[position++] = (byte)((what>>16)&0xff);
		bb[position++] = (byte)((what>>24)&0xff);
	}
	
	public void setBigEndian32(int where, int what)  {
		bb[where] 		= (byte)((what>>24)&0xff);
		bb[where+1] 	= (byte)((what>>16)&0xff);
		bb[where+2]	= (byte)((what>>8)&0xff);
		bb[where+3]	= (byte)(what&0xff);
	}

	public void setBigEndian32(int what)  {
		bb[position++] = (byte)((what>>24)&0xff);
		bb[position++] = (byte)((what>>16)&0xff);
		bb[position++] = (byte)((what>>8)&0xff);
		bb[position++] = (byte)(what&0xff);
	}
	    
	public void incrementPosition(int length) {

	if (length > size || length+position > size || length <0) {
		throw new ArrayIndexOutOfBoundsException();
	}
	position+=length;
	}

	public void setPosition(int position) {
	if (position > size || position <0) {
		throw new ArrayIndexOutOfBoundsException();
		}
	this.position = position;
	}
	
	public String toString(){
		String s = "";
		for(int i=start;i<bb.length;i++){
		String hex = Integer.toHexString(bb[i]&0xff);
		if(hex.length()==1) hex = "0"+hex;
		s += (hex+" ");
		}
	return s;
	}
}
