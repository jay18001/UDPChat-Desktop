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
import java.util.Optional;
import java.util.Date;


public class Message {
  public MessageType type;
  @JsonIgnore
  public Optional<String> value = Optional.empty();
  public String user;
  public Date timestamp;
  
  @JsonIgnore
  public static Message build(MessageType type, String value, String user) {
      Message message = new Message();
      message.type = type;
      message.value = Optional.of(value);
      message.user = user;
      message.timestamp = new Date();
      return message;
  }
  
  @JsonIgnore
  public static Message build(MessageType type, String user) {
      Message message = new Message();
      message.type = type;
      message.value = Optional.empty();
      message.user = user;
      message.timestamp = new Date();
      return message;
  }
  
  @JsonProperty("value")
  private void setParsedValue(String value) {
      if (value == null) {
        this.value = Optional.empty();
      } else {
        this.value = Optional.of(value);
      }
  }
  
  @JsonProperty("value")
  private String getParsedValue() {
     if (value.isPresent()) {
         return value.get();
     }
     return null;
  }
  
}