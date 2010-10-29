/* VChannel.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:39 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Abstract class for RDP5 channels
 */
package com.elusiva.rdp.rdp5;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.elusiva.rdp.Constants;
import com.elusiva.rdp.Input;
import com.elusiva.rdp.Common;
import com.elusiva.rdp.Options;
import com.elusiva.rdp.RdesktopException;
import com.elusiva.rdp.RdpPacket;
import com.elusiva.rdp.RdpPacket_Localised;
import com.elusiva.rdp.Secure;
import com.elusiva.rdp.crypto.CryptoException;

public abstract class VChannel {

	protected static Logger logger = Logger.getLogger(Input.class);
	
	private int mcs_id = 0;
    protected Secure secureLayer;
	
    /**
     * Provide the name of this channel
     * @return Channel name as string
     */
	public abstract String name();
	
    /**
     * Provide the set of flags specifying working options for this channel
     * @return Option flags
     */
    public abstract int flags();
    
    /**
     * Process a packet sent on this channel
     * @param data Packet sent to this channel
     * @throws RdesktopException
     * @throws IOException
     * @throws CryptoException
     */
	public abstract void process(RdpPacket data) throws RdesktopException, IOException, CryptoException;
	public int mcs_id(){
		return mcs_id;
	}
	
    /**
     * Set the MCS ID for this channel
     * @param mcs_id New MCS ID
     */
	public void set_mcs_id(int mcs_id){
		this.mcs_id = mcs_id;
	}

    public void setSecureLayer(Secure secureLayer) {
		this.secureLayer = secureLayer;
	}

    protected Secure getSecureLayer() {
        assert(this.secureLayer != null);
        return this.secureLayer;
    }
	
    /**
     * Initialise a packet for transmission over this virtual channel
     * @param length Desired length of packet
     * @param option
     * @return Packet prepared for this channel
     * @throws RdesktopException
     */
	public RdpPacket_Localised init(int length, Options option) throws RdesktopException{
		RdpPacket_Localised s;
		
		s = getSecureLayer().init(option.isEncryptionEnabled() ? Secure.SEC_ENCRYPT : 0,length + 8);
		s.setHeader(RdpPacket.CHANNEL_HEADER);
		s.incrementPosition(8);
				
		return s;
	}
		
    /**
     * Send a packet over this virtual channel
     * @param data Packet to be sent
     * @throws RdesktopException
     * @throws IOException
     * @throws CryptoException
     */
	public void send_packet(RdpPacket_Localised data) throws RdesktopException, IOException, CryptoException
	{
		if(getSecureLayer() == null) return;
		int length = data.size();
		
		int data_offset = 0;
		int packets_sent = 0;
		int num_packets = (length/VChannels.CHANNEL_CHUNK_LENGTH);
		num_packets += length - (VChannels.CHANNEL_CHUNK_LENGTH)*num_packets;
		
		while(data_offset < length){
		
			int thisLength = Math.min(VChannels.CHANNEL_CHUNK_LENGTH, length - data_offset);
			
			RdpPacket_Localised s = getSecureLayer().init(Constants.encryption ? Secure.SEC_ENCRYPT : 0, 8 + thisLength);
			s.setLittleEndian32(length);
		
			int flags = ((data_offset == 0) ? VChannels.CHANNEL_FLAG_FIRST : 0);
			if(data_offset + thisLength >= length) flags |= VChannels.CHANNEL_FLAG_LAST;
			
			if ((this.flags() & VChannels.CHANNEL_OPTION_SHOW_PROTOCOL) != 0) flags |= VChannels.CHANNEL_FLAG_SHOW_PROTOCOL;
		
			s.setLittleEndian32(flags);
			s.copyFromPacket(data,data_offset,s.getPosition(),thisLength);
			s.incrementPosition(thisLength);
			s.markEnd();
			
			data_offset += thisLength;		
			
			if(getSecureLayer() != null) getSecureLayer().send_to_channel(s, Constants.encryption ? Secure.SEC_ENCRYPT : 0, this.mcs_id());
			packets_sent++;
		}
	}
	
}
