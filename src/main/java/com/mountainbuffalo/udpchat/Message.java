/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mountainbuffalo.udpchat;

import com.fasterxml.jackson.annotation.*;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * A network message object.
 *
 * @author Justin
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Message {

    private final MessageType type;
    private final String user;
    @JsonProperty("value")
    private final String value;
    private final Date timestamp;
    
    private Message() {
        this(MessageType.INVALID, "");
    }

    public Message(MessageType type, String user) {
        this(type, user, "");
    }
    
    public Message(MessageType type, String user, String value) {
        this(type, user, value, new Date());
    }
    
    public Message(MessageType type, String user, String value, Date timestamp) {
        // Validate.
        Objects.requireNonNull(type, "The message type may not be null!");
        Objects.requireNonNull(user, "The message user may not be null!");
        Objects.requireNonNull(value, "The message value may not be null!");
        Objects.requireNonNull(timestamp, "The message timestamp may not be null!");
        
        // Assign.
        this.type = type;
        this.user = user;
        this.value = value;
        this.timestamp = timestamp;
    }

    public MessageType getType() {
        return type;
    }
    
    public String getUser() {
        return user;
    }

    @JsonIgnore
    public Optional<String> getValue() {
        return Optional.of(value);
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
