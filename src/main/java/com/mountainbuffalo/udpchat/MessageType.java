/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mountainbuffalo.udpchat;

import com.fasterxml.jackson.annotation.*;

/**
 * Application Message Type Enumeration.
 *
 * @author Justin
 */
public enum MessageType {

    @JsonProperty("new_user")
    NEW_USER,
    @JsonProperty("message")
    MESSAGE,
    @JsonProperty("user_left")
    USER_LEFT,
    @JsonProperty("invalid")
    INVALID;

}
