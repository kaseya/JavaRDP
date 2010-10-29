/* JRdpLoader.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:42 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Launch ProperJavaRDP with settings from a config file
 */
package com.elusiva.rdp.loader;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.StringTokenizer;

import com.elusiva.rdp.*;

public class JRdpLoader {

	// Set of identifiers to be found within the launch file
	private static String[] identifiers = {
			"--user", 
			"--password",
			"--domain",
			"--fullscreen",
			"--geometry",
			"--use_rdp5"
	};
	
	// Set of command-line options mapping to the launch file identifiers
	private static String[] pairs = {
			"-u",
			"-p",
			"-d",
			"-f",
			"-g",
			"--use_rdp5"
	};
	
	public static void main(String args[]){
		
		if(args.length <= 0){
			System.err.println("Expected usage: JRdpLoader launchFile");
			System.exit(-1);
		}
		
		String launchFile = args[0];
	
		String server = ""; String port = "";
		
		try{
			String outArgs = "";
			
			// Open the file specified at the command-line
			FileInputStream fstream = new FileInputStream(launchFile);
			DataInputStream in = new DataInputStream(fstream);
			while (in.available() != 0) {
				String line = in.readLine();
				StringTokenizer stok = new StringTokenizer(line);
				if(stok.hasMoreTokens()){
					String identifier = stok.nextToken();
					String value = "";
					while(stok.hasMoreTokens()){
						value += stok.nextToken();
						if(stok.hasMoreTokens()) value += " ";
					}
				
					if(identifier.equals("--server")) server = value;
					else if(identifier.equals("--port")) port = value;
					else{	
						String p = getParam(identifier);
						if(p != null) outArgs += p + " " + value + " ";
					}
				}
			}
			
			if(server != null && server != ""){
				outArgs += server;
				if(port != null && port != "") outArgs += ":" + port;
				
                //String[] finArgs = outArgs.split(" ");
                String[] finArgs = Utilities_Localised.split(outArgs, " ");
                
                Rdesktop.main(finArgs, null);
				in.close();
			}else{ System.err.println("No server name provided"); System.exit(0); }
			
        
		}
		catch(IOException ioe){ System.err.println("Launch file could not be read: " + ioe.getMessage()); System.exit(-1); } 
		catch(Exception e){ e.printStackTrace(); System.exit(-1); }
	}
	
	private static String getParam(String identifier){
		for(int i = 0; i < identifiers.length; i++){
			if(identifier.equals(identifiers[i])){
				return pairs[i];
			}
		}
		
		return null;
	}
	
}
