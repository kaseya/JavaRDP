/* ISO.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:53 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Java 1.4 specific extension of ISO class
 */
// Created on 05-Aug-2003

package com.elusiva.rdp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ISO_Localised extends ISO {

	/*
	protected Socket negotiateSSL(Socket sock) throws Exception {
	     // The default host/port to connect to
	     String host="localhost";
	     int port=4433;
	     String keyfile="client.pem";
	     String rootfile="root.pem";
	     String randomfile="random.pem";
	     String password="password";
	     LongOpt Longopts[]=new LongOpt[13];
	     int iterate=1;
	     boolean acceptunverified=false;
	     boolean fakeseed=false;
	     boolean checkDates=false;
	     short[] cipherSuites=null;
	     int delay=0;
	     boolean negotiateTLS=true;
		
		SSLContext ctx=new SSLContext();
		SSLPolicyInt policy=new SSLPolicyInt();
	       
		if(cipherSuites!=null)
			policy.setCipherSuites(cipherSuites);
	        
		policy.acceptUnverifiableCertificates(true);    
		policy.negotiateTLS(negotiateTLS);
		ctx.setPolicy(policy);
		
		
		SSLSocket s = null;
		
		s = new SSLSocket(ctx,sock,host,port,SSLSocket.CLIENT);
		logger.info("Layered SSL socket on existing socket");			 
			 
		 Vector cc=s.getCertificateChain();
		 int cs=s.getCipherSuite();
		 
		 System.out.println("Cipher suite: "+SSLPolicyInt.getCipherSuiteName
		   (cs));
		 
		 if(cc!=null){
		   System.out.println("Cert chain");
		 
		   for(int i=0;i<cc.size();i++){
		     X509Cert cert=(X509Cert)cc.elementAt(i);

		     System.out.println("Issuer "+cert.getIssuerName().getNameString());
		     System.out.println("Subject "+cert.getSubjectName().getNameString());
		     System.out.println("Serial "+cert.getSerial());
		     System.out.println("Validity "+cert.getValidityNotBefore() +"-"+
		       cert.getValidityNotAfter());

		   }
		 }
		 System.out.println("-----");
		
		return s;
		
	}*/
	
protected void doSocketConnect(InetAddress host, int port)throws IOException{
	int timeout_ms = 3000; // timeout in milliseconds
	
	rdpsock = new Socket();
	rdpsock.connect(new InetSocketAddress(host,port),timeout_ms);
}

}
