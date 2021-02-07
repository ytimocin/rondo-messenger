package com.rondo.messenger.controller;

import com.rondo.messenger.domain.IncomingMessage;
import com.rondo.messenger.domain.OutgoingMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class IncomingMessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public OutgoingMessage reply(IncomingMessage incomingMessage) {
        return OutgoingMessage.builder()
                .content("Hello, " + incomingMessage.getName())
                .build();
    }

}
