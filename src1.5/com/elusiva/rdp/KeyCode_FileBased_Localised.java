/* KeyCode_FileBased_Localised.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:54 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.4 specific extension of KeyCode_FileBased class
 */
package com.elusiva.rdp;

import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.HashMap;

import com.elusiva.rdp.keymapping.KeyCode_FileBased;
import com.elusiva.rdp.keymapping.KeyMapException;


public class KeyCode_FileBased_Localised extends KeyCode_FileBased {

	private HashMap keysCurrentlyDown = new HashMap();
	
	/**
	 * @param fstream
	 * @param option
     * @throws KeyMapException
	 */
	public KeyCode_FileBased_Localised(InputStream fstream, Options option) throws KeyMapException {
		super(fstream, option);
	}

	public KeyCode_FileBased_Localised(String s, Options option) throws KeyMapException{
		super(s, option);
	}
	
	private void updateCapsLock(KeyEvent e){
		if(option.isUseLockingKeyStateEnabled()){
			try {
				option.enableUseLockingKeyState();
				capsLockDown = e.getComponent().getToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
			} catch (Exception uoe){ option.disableUseLockingKeyState(); }
		}
	}
	
}
