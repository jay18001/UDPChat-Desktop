/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mountainbuffalo.udpchat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;

/**
 *
 * @author Ryan
 */
public class MessageSerializer {
    
    private final ObjectMapper mapper;

    public MessageSerializer() {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public String serialize(Message message) throws IOException {
        return this.mapper.writeValueAsString(message);
    }

    public Message deserialize(String rawdata) throws IOException {
        return this.mapper.readValue(rawdata, Message.class);
    }
    
}
