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

import com.fasterxml.jackson.annotation.*;

public enum MessageType {
    NEW_USER("new_user"), 
    MESSAGE("message"),
    USER_LEFT("user_left");
    
    private String key;

    MessageType(String key) {
        this.key = key;
    }

    @JsonCreator
    public static MessageType fromString(String key) {
        return key == null
                ? null
                : MessageType.valueOf(key.toUpperCase());
    }

    @JsonValue
    public String getKey() {
        return key;
    } 
}
