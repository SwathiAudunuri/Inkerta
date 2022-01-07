package com.tecnics.einvoice.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.tecnics.einvoice.chat.Message;
import com.tecnics.einvoice.chat.OutputMessage;
import com.tecnics.einvoice.model.ResponseMessage;

@Controller
public class WSController {

   @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public String send(String message) throws Exception {
	   System.out.println("message = " + message);
        return "test";
    } 
	
    
}