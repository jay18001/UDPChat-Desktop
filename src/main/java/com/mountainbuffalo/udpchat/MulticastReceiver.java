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
import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class MulticastReceiver extends Thread implements Closeable {

    private final byte[] buffer = new byte[Settings.BUFFER_SIZE];

    private final Logger logger;
    private final int port;
    private final InetAddress group;
    private final MulticastSocket socket;
    private final MessageSerializer serializer;
    private final MulticastReceiverDelegate delegate;

    public MulticastReceiver(int port, InetAddress group, MessageSerializer serializer, MulticastReceiverDelegate delegate) throws IOException {
        // Validate the address.
        Objects.requireNonNull(group, "The address of the group to join may not be null!");
        Objects.requireNonNull(serializer, "The message serializer instance may not be null!");
        Objects.requireNonNull(delegate, "The connection delegate may not be null!");

        // Assign parameters.
        this.logger = Logger.getLogger("MulticastConnection " + group + ":" + port);
        this.port = port;
        this.group = group;
        this.serializer = serializer;
        this.delegate = delegate;

        // Create the connection.
        this.socket = new MulticastSocket(port);
        
        // Join the group.
        try {
            this.socket.joinGroup(group);
        } catch (IOException ex) {
            this.logger.log(Level.SEVERE, "Unable to join group: {0}", group);
            throw ex;
        }
    }

    @Override
    public void close() {
        // Step 0. Close the connection.
        this.socket.close();
    }

    @Override
    public void run() {
        // Log Start
        this.logger.log(Level.INFO, "Multicast Connection Started!");
        
        // Loop
        while (true) {
            try {
                final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                this.socket.receive(packet);
                final String rawdata = new String(packet.getData(), packet.getOffset(), packet.getLength());
                this.logger.log(Level.INFO, "Recieved Raw JSON: {0}", rawdata);
                final Message message = this.serializer.deserialize(rawdata);
                this.delegate.didReciveMessage(message);
            } catch (SocketException ex) {
                this.logger.log(Level.INFO, "Multicast Connection Closed!");
                break;
            } catch (IOException ex) {
                this.logger.log(Level.SEVERE, "Multicast Connection Terminated!\n\tReason: {0}", ex.getMessage());
                break;
            }
        }
        
        // Log Stop
        this.logger.log(Level.INFO, "Multicast Connection Ended!");
    }

    public void sendMessage(Message message) {
        // Validate.
        Objects.requireNonNull(message, "Cannot send a null message!");

        // Serialize.
        final String rawData;
        try {
            rawData = this.serializer.serialize(message);
        } catch (IOException e) {
            this.logger.log(Level.WARNING, "Unable to serialize message: {0}", message);
            return;
        }

        // Encode.
        final byte[] encoded = rawData.getBytes();

        // Transmit.
        // Do Stuff.
        try {
            socket.send(new DatagramPacket(encoded, encoded.length, group, port));
        } catch (IOException ex) {
            Logger.getLogger(MulticastReceiver.class.getName()).log(Level.SEVERE, "Unable to transmit message!\n\tMessage: {0}\n\tCause: {1}", new Object[]{rawData, ex});
        }
    }

}
