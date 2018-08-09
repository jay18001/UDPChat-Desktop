package com.mountainbuffalo.udpchat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Justin
 */

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.*;
import java.util.Base64;

public class MulticastReceiver extends Thread {
    protected MulticastSocket socket = null;
    protected byte[] buf = new byte[8192];
    private InetAddress group;
    public MulticastReceiverDelegate delegate;
    private final String groupAdress = "239.225.236.1";
    private final int groupPort = 3240;
    private ObjectMapper mapper;
    
    public MulticastReceiver() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS , false);
    }
    
    public void open() {
        try {
         socket = new MulticastSocket(groupPort);
         group = InetAddress.getByName(groupAdress);
         socket.joinGroup(group);
        } catch (IOException ex) {
            Logger.getLogger(MulticastReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() {
        try {
            socket.leaveGroup(group);
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(MulticastReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String input = new String(packet.getData(), 0, packet.getLength());
                byte[] decoded = Base64.getDecoder().decode(input);
                String output = new String(decoded);
                
                System.out.println(output);
                Message value = mapper.readValue(output, Message.class);
                delegate.didReciveMessage(value);
                if ("end".equals(output)) {
                    break;
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(MulticastReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMessage(Message message) {
        try {
            byte[] sendBuf = Base64.getEncoder().encode(mapper.writeValueAsBytes(message));
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, group, groupPort);
            socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(MulticastReceiver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}