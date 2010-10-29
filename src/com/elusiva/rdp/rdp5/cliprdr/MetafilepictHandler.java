/* MetafilepictHandler.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:41 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: 
 */
package com.elusiva.rdp.rdp5.cliprdr;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.elusiva.rdp.RdpPacket;

public class MetafilepictHandler extends TypeHandler {

	/* Mapping Modes */
	public static final int MM_TEXT = 1;
	public static final int MM_LOMETRIC = 2;
	public static final int MM_HIMETRIC = 3;
	public static final int MM_LOENGLISH = 4;
	public static final int MM_HIENGLISH = 5;
	public static final int MM_TWIPS = 6;
	public static final int MM_ISOTROPIC = 7;
	public static final int MM_ANISOTROPIC = 8;
	
	String[] mapping_modes = {
			"undefined",
			"MM_TEXT",
			"MM_LOMETRIC",
			"MM_HIMETRIC",
			"MM_LOENGLISH",
			"MM_HIENGLISH",
			"MM_TWIPS",
			"MM_ISOTROPIC",
			"MM_ANISOTROPIC"
	};
	
	public boolean formatValid(int format) {
		return (format == CF_METAFILEPICT);
	}

	public boolean mimeTypeValid(String mimeType) {
		return mimeType.equals("image");
	}

	public int preferredFormat() {
		return CF_METAFILEPICT;
	}

	public Transferable handleData(RdpPacket data, int length) {
		String thingy = "";
		OutputStream out = null;
		
		//System.out.print("Metafile mapping mode = ");
		int mm = data.getLittleEndian32();
		//System.out.print(mapping_modes[mm]);
		int width = data.getLittleEndian32();
		//System.out.print(", width = " + width);
		int height = data.getLittleEndian32();
		//System.out.println(", height = " + height);
		
		try {
			out = new FileOutputStream("test.wmf");
		
			for(int i = 0; i < (length-12); i++){
				int aByte = data.get8();
				out.write(aByte);
				thingy += Integer.toHexString(aByte & 0xFF) + " ";
			}
			//System.out.println(thingy);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return(new StringSelection(thingy));
	}

	public String name() {
		return "CF_METAFILEPICT";
	}


	public byte[] fromTransferable(Transferable in) {
		return null;
	}

	public void handleData(RdpPacket data, int length, ClipInterface c) {
		String thingy = "";
		OutputStream out = null;
		
		//System.out.print("Metafile mapping mode = ");
		int mm = data.getLittleEndian32();
		//System.out.print(mapping_modes[mm]);
		int width = data.getLittleEndian32();
		//System.out.print(", width = " + width);
		int height = data.getLittleEndian32();
		//System.out.println(", height = " + height);
		
		try {
			out = new FileOutputStream("test.wmf");
		
			for(int i = 0; i < (length-12); i++){
				int aByte = data.get8();
				out.write(aByte);
				thingy += Integer.toHexString(aByte & 0xFF) + " ";
			}
			//System.out.println(thingy);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.elusiva.rdp.rdp5.cliprdr.TypeHandler#send_data(java.awt.datatransfer.Transferable, com.elusiva.rdp.rdp5.cliprdr.ClipInterface)
	 */
	public void send_data(Transferable in, ClipInterface c) {
		c.send_null(ClipChannel.CLIPRDR_DATA_RESPONSE,ClipChannel.CLIPRDR_ERROR);
	}

}
