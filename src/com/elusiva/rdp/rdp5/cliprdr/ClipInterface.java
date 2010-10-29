/* ClipInterface.java
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

import java.awt.datatransfer.Transferable;

public interface ClipInterface {
	
	public void copyToClipboard(Transferable t);
	public void send_data(byte []data, int length);
	public void send_null(int type, int status);
}
