/* BMPToImageThread.java
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

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import com.elusiva.rdp.RdpPacket;
	 public class BMPToImageThread extends Thread {
	 	
	 	RdpPacket data; int length; ClipInterface c;
	 	
	 	public BMPToImageThread(RdpPacket data, int length, ClipInterface c){
	 		super();
	 		this.data = data;
	 		this.length = length;
	 		this.c = c;
	 	}
	 	
	 	public void run(){
	 		String thingy = "";
			OutputStream out = null;
				
			int origin = data.getPosition();
			
			int head_len = data.getLittleEndian32();

			data.setPosition(origin);
			
			byte[] content = new byte[length];
			
				for(int i = 0; i < length; i++){
					content[i] = (byte) (data.get8() & 0xFF);
				}
				
				Image img = ClipBMP.loadbitmap(new ByteArrayInputStream(content));
			    ImageSelection imageSelection = new ImageSelection(img);
			    c.copyToClipboard(imageSelection);
	 	}
	 	
	 }
