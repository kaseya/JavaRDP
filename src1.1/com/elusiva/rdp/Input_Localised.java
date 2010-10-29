/* Input_Localised.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:59 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.1 specific extension of Input class
 */
// Created on 03-Sep-2003

package com.elusiva.rdp;

import com.elusiva.rdp.keymapping.KeyCode;
import com.elusiva.rdp.keymapping.KeyCode_FileBased;

public class Input_Localised extends Input {

	public Input_Localised(RdesktopCanvas c, Rdp r, String k){
		super(c,r,k);
	}
	
	public Input_Localised(RdesktopCanvas c, Rdp r, KeyCode_FileBased k){
		super(c,r,k);
	}
}
