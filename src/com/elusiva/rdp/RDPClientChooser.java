/* RDPClientChooser.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:20 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Selects and loads a native RDP client if available
 */
package com.elusiva.rdp;

import org.apache.log4j.Logger;
import org.apache.log4j.Level;
import java.io.*;
import gnu.getopt.*;

public class RDPClientChooser {
	static Logger logger = Logger.getLogger(RDPClientChooser.class);
    
    /**
     * Initialise a client chooser, set logging level to DEBUG
     */
	public RDPClientChooser() {
    	logger.setLevel(Level.DEBUG);
		logger.info("RDPClientChooser");
    }

    /**
     * Public method to locate and run a native RDP client (currently only on Mac OS)
     * @param args Arguments to provide to native client
     * @param option
     */
	public boolean RunNativeRDPClient(String[] args, Options option)
	{
		logger.info("RDPClientChooser.RunNativeRDPClient");
		
		String os = System.getProperty("os.name");
		String osvers = System.getProperty("os.version");
		
		// For Mac OS X we try to run the Microsoft Remote Desktop Connection RDP client if it exists

		if(os.startsWith("Mac OS X")) {
			if (new File("/Applications/Remote Desktop Connection").exists()) {
				logger.info("MAC OS X Microsoft Remote Desktop Connection exists");
				try {
					return RunMacRemoteDesktopConnection(args, option);
				}
				catch (IOException e) {
					logger.info("MAC OS X Microsoft Remote Desktop Connection not found");
					return false;
				}
			} else {
				logger.info("MAC OS X Microsoft Remote Desktop Connection not found");
			}
		}

		// For Linux we need to identify an appropriate native client
		
		if(os.startsWith("Linux")) {
		
			// TBD
		
		}		
		
		return false;
	}
	
	//
	// Private Class method to run the Microsoft MAC OS X Remote Desktop Connection (RDP) Client
	//
	
    /**
     * Private method to run the Mac OS RDP client provided by Microsoft
     * @param args Arguments to provide to native client
     * @param option
     */
	private boolean RunMacRemoteDesktopConnection(String[] args, Options option)
	throws IOException 
	{
		logger.info("RunMacRemoteDesktopConnection()");
		
		LongOpt[] alo = new LongOpt[4];
		int c;
		String arg;
		
		option.setWindowTitle("Remote Desktop Connection");

		// Process arguments (there are more than we need now - need to reduce - also need to check for correct args)
		
		Getopt g = new Getopt ("properJavaRDP", args, "bc:d:f::g:k:l:n:p:s:t:T:u:", alo);
		
		while ((c = g.getopt()) != -1) 
		{
			switch (c) {
			
				case 'd':
					option.setDomain(g.getOptarg());
					break;	
					
				case 'n':
					option.setHostname(g.getOptarg());
					break;

				case 'p':
					option.setPassword(g.getOptarg());
					break;	

				case 't':
					arg = g.getOptarg();
					try {
						option.setPort(Integer.parseInt(arg));
					}
					catch (Exception e) {
					}
					break;
					
				case 'T':
					option.setWindowTitle(g.getOptarg().replace('_',' '));
					break;

				case 'u':
					option.setUsername(g.getOptarg());
					break;

				case '?':
				default:
					break;
			
			}
		}
		
		// Obtain Server name and possibly port from command args
		
		String server = null;
	
		if (g.getOptind() < args.length) {
			int colonat = args[args.length-1].indexOf(":", 0);
			if (colonat == -1){
				server = args[args.length-1];
			}
			else{
				server = args[args.length-1].substring(0,colonat);
				option.setPort(Integer.parseInt(args[args.length-1].substring(colonat+1)));
			}
		}
		else {
			logger.warn("Server name required");
			return false;
		}
			
		// Create a temporary directory from which to run RDC - we do this so that
		// we can run multiple instances
		
		String rdproot = "/var/tmp/RDP-"+ option.getHostname() +"-"+ option.getPort();
		
		try {
			new File(rdproot).mkdir();
		} 
		catch (Exception e) {
			logger.warn("Failed to create directory "+rdproot);
			return false;
		}
		
		// Dynamically create the RDP config file based on args passed.
		
		logger.info("Creating RDP Config in "+rdproot);

		FileWriter rdpConfigFile = new FileWriter(rdproot+"/Default.rdp");
		
		rdpConfigFile.write("screen mode id:i:0\n");
		rdpConfigFile.write("startdisplay:i:0\n");
		rdpConfigFile.write("desktop size id:i:6\n"); //full screen - this needs to be mapped from geometry param passed in TBD
		rdpConfigFile.write("desktopwidth:i:1280\n");
		rdpConfigFile.write("desktopheight:i:854\n");
		rdpConfigFile.write("autoshowmenu:i:1\n");
		rdpConfigFile.write("desktopallowresize:i:1\n");
		rdpConfigFile.write("session bpp:i:8\n");	// 256 colors
		rdpConfigFile.write("winposstr:s:0,3,0,0,800,600\n");
		rdpConfigFile.write("auto connect:i:1\n");
		rdpConfigFile.write("full address:s:"+server+":"+ option.getPort() +"\n");
		rdpConfigFile.write("compression:i:1\n");
		rdpConfigFile.write("rightclickmodifiers:i:4608\n");
		rdpConfigFile.write("altkeyreplacement:i:0\n");
		rdpConfigFile.write("audiomode:i:1\n");
		rdpConfigFile.write("redirectdrives:i:1\n");
		rdpConfigFile.write("redirectprinters:i:1\n");
		rdpConfigFile.write("username:s:"+ option.getUsernameImpl() +"\n");
		rdpConfigFile.write("clear password:s:"+ option.getPassword() +"\n");
		rdpConfigFile.write("domain:s:"+ option.getDomain() +"\n");
		rdpConfigFile.write("alternate shell:s:\n");
		rdpConfigFile.write("shell working directory:s:\n");
		rdpConfigFile.write("preference flag id:i:2\n");
		rdpConfigFile.write("disable wallpaper:i:1\n");
		rdpConfigFile.write("disable full window drag:i:0\n");
		rdpConfigFile.write("disable menu anims:i:0\n");
		rdpConfigFile.write("disable themes:i:0\n");
		rdpConfigFile.write("disable cursor setting:i:0\n");
		rdpConfigFile.write("bitmapcachepersistenable:i:1\n");
		
		rdpConfigFile.write("Min Send Interval:i:5\n");
		rdpConfigFile.write("Order Draw Threshold:i:5\n");
		rdpConfigFile.write("Max Event Count:i:150\n");
		rdpConfigFile.write("Normal Event Count:i:150\n");
		rdpConfigFile.write("BitMapCacheSize:i:3500\n");
		
		rdpConfigFile.write("Keyboard Layout:i:en-uk\n");
				
		rdpConfigFile.close();

		//
		// Set recent servers indicating that local drives are accessable This avoids the warning
		// and prevents a growing list of server:port entries 
		//
		
		if (new File(System.getProperty("user.home")+"/Library/Preferences/Microsoft/RDC Client").exists()) {		
			FileWriter recentServersFile = new FileWriter(System.getProperty("user.home")+"/Library/Preferences/Microsoft/RDC Client/Recent Servers");
			recentServersFile.write(server+"\r1\r");
			recentServersFile.close();
		} 
	
		// Copy the RDP Client application to a temporary directory to allow multiple copies to run. Note here that we use the MAC OS X ditto command because
		// a normal copy of the executable would not copy the advanced OS X attributes which (among other things) denote the file as an "application".
		
		String[] appcopycmd = {"/bin/sh", "-c", "ditto -rsrc /Applications/Remote\\ Desktop\\ Connection/Remote\\ Desktop\\ Connection "+rdproot+"/ >/dev/null 2>/dev/null"};

		try {
			Process p = Runtime.getRuntime().exec(appcopycmd);
		}
		catch (IOException e) {
			logger.warn("Unable to copy application to temporary directory");
			return false;
		}
		
		try {		
			Process p = Runtime.getRuntime().exec(appcopycmd);
			logger.warn("RDP Client copied to "+rdproot);
			try 
			{
				p.waitFor(); // Wait for the command to complete
			}
			catch (InterruptedException e)
			{
				logger.warn("Unable to wait for application to copy");
				return false;
			}
		} 
		catch (IOException e) {
			logger.warn("Unable to copy application to temporary directory");
			return false;
		}


		// Move the application to the name of the title so that the running application shows when using ALT-TAB etc.
		
		String[] mvcmd = {"/bin/sh", "-c", "mv "+rdproot+"/Remote\\ Desktop\\ Connection '"+rdproot+"/"+ option.getWindowTitle() +"' >/dev/null 2>/dev/null"};

		try {		
			Process p = Runtime.getRuntime().exec(mvcmd);
			try 
			{
				p.waitFor(); // Wait for the mv command to complete
			}
			catch (InterruptedException e)
			{
				logger.warn("Unable to wait for application to run");
				return false;
			}
		} 
		catch (IOException e) {
			logger.warn("Unable to move application");
			return false;
		}
		
		// Run an instance of the RDP Client using the Mac OS X "open" command
		
		String[] rdpcmd = {"/bin/sh", "-c", "open -a '"+rdproot+"/"+ option.getWindowTitle() +"' "+rdproot+"/Default.rdp >/dev/null 2>/dev/null"};
		
		try {		
			Process p = Runtime.getRuntime().exec(rdpcmd);
			logger.info("RDP Client Launched from "+rdproot);
			try 
			{
				p.waitFor(); // Wait for the open command to complete
			}
			catch (InterruptedException e)
			{
				logger.warn("Unable to wait for application to run");
				return false;
			}
		} 
		catch (IOException e) {
			logger.warn("Unable to open (run) application");
			return false;
		}
		
		// Sleep 10 seconds to give the RDP client a chance to start before we remove the temp directory.

		try { 
			Thread.sleep(10000); 
		}
		catch (Exception e) {
			logger.info("Unable to wait for 10 seconds");		
			return false;
		}

		// Remove the tempory directory

		String[] rmcmd = {"/bin/sh", "-c", "rm -r "+rdproot+" >/dev/null 2>/dev/null"};
		
		try {		
			Process p = Runtime.getRuntime().exec(rmcmd);
		} 
		catch (IOException e) {
			logger.warn("Unable to remove temporary directory "+rdproot);
			return true;
		}
		
		logger.warn("RDP Client Completed");		
		return true;
	}
}
