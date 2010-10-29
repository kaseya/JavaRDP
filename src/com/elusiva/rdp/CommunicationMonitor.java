/* CommunicationMonitor.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:19 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Provide a lock for network communications,
 *          used primarily by the Clipboard channel to
 *          prevent sending of mouse/keyboard input when
 *          sending large amounts of clipboard data.
 */

package com.elusiva.rdp;

public class CommunicationMonitor {

	public static Object locker = null;
	
    /**
     * Identify whether or not communications are locked
     * @return True if locked
     */
	public static boolean locked(){
		return locker != null;
	}
	
    /**
     * Wait for a lock on communications
     * @param o Calling object should supply reference to self
     */
	public static void lock(Object o){
		if(locker == null) locker = o;
		else{
			while(locker != null){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("InterruptedException: " + e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	
    /**
     * Unlock communications, only permitted if the caller holds the current lock
     * @param o Calling object should supply reference to self
     * @return
     */
	public static boolean unlock(Object o){
		if(locker == o){
			locker = null;
			return true;
		}
		return false;
	}
	
	
}
