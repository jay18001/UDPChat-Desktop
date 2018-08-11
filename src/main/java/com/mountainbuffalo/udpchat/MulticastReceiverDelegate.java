/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mountainbuffalo.udpchat;

/**
 * Interface for multi-cast message listeners.
 * 
 * @author Justin
 */
public interface MulticastReceiverDelegate {

    void didReciveMessage(Message message);
    
}
