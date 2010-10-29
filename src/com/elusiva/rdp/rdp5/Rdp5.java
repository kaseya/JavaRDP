/* Rdp5.java
 * Component: ProperJavaRDP
 * 
 * Revision: $Revision: 1.1.1.1 $
 * Author: $Author: suvarov $
 * Date: $Date: 2007/03/08 00:26:39 $
 *
 * Copyright (c) 2005 Propero Limited
 *
 * Purpose: Handle RDP5 orders
 */

package com.elusiva.rdp.rdp5;

import com.elusiva.rdp.*;

public class Rdp5 extends Rdp {

    private VChannels channels;

    /**
     * Initialise the RDP5 communications layer, with specified virtual channels
     *
     * @param channels
     *            Virtual channels for RDP layer
     */
    public Rdp5(VChannels channels, Options option) {
        super(channels, option);
        this.channels = channels;
        
    }

    public Rdp5(Options option, Secure secureLayer) {
        super(option, secureLayer);
    }

    /**
     * Process an RDP5 packet from a virtual channel
     * @param s Packet to be processed
     * @param channelno Channel on which packet was received
     */
    void rdp5_process_channel(RdpPacket_Localised s, int channelno) {
        VChannel channel = channels.find_channel_by_channelno(channelno);
        if (channel != null) {
            try {
                channel.process(s);
            } catch (Exception e) {
            }
        }
    }

}
