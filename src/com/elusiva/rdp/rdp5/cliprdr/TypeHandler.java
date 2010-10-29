/* TypeHandler.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:40 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: 
 */
package com.elusiva.rdp.rdp5.cliprdr;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import com.elusiva.rdp.RdpPacket;

public abstract class TypeHandler {
	
	/* Clipboard constants, "borrowed" from GCC system headers in 
	   the w32 cross compiler */
	
	// Format constants
	public static final int CF_TEXT = 1;
	public static final int CF_BITMAP = 2;
	public static final int CF_METAFILEPICT = 3;
	public static final int CF_SYLK = 4;
	public static final int CF_DIF = 5;
	public static final int CF_TIFF = 6;
	public static final int CF_OEMTEXT = 7;
	public static final int CF_DIB = 8;
	public static final int CF_PALETTE = 9;
	public static final int CF_PENDATA = 10;
	public static final int CF_RIFF = 11;
	public static final int CF_WAVE = 12;
	public static final int CF_UNICODETEXT = 13;
	public static final int CF_ENHMETAFILE = 14;
	public static final int CF_HDROP = 15;
	public static final int CF_LOCALE = 16;
	public static final int CF_MAX = 17;
	public static final int CF_OWNERDISPLAY = 128;
	public static final int CF_DSPTEXT = 129;
	public static final int CF_DSPBITMAP = 130;
	public static final int CF_DSPMETAFILEPICT = 131;
	public static final int CF_DSPENHMETAFILE = 142;
	public static final int CF_PRIVATEFIRST = 512;
	public static final int CF_PRIVATELAST = 767;
	public static final int CF_GDIOBJFIRST = 768;
	public static final int CF_GDIOBJLAST = 1023;
	
	public abstract String name();
	public abstract void handleData(RdpPacket data, int length, ClipInterface c);
	public abstract int preferredFormat();
	public abstract boolean formatValid(int format);
	public abstract boolean mimeTypeValid(String mimeType);

	public abstract void send_data(Transferable in, ClipInterface c);	
	
	public boolean clipboardValid(DataFlavor[] dataTypes) {
	
		for(int i = 0; i < dataTypes.length; i++){
			if(mimeTypeValid(dataTypes[i].getPrimaryType())) return true;
		}
		return false;
	}
}
