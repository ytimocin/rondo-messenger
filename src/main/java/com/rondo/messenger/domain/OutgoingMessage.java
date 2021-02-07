package com.rondo.messenger.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class OutgoingMessage {

    private String from;
    private String message;
    private long timestamp;

    @Override
    public String toString() {
        return "Message: " + getMessage() + " From: " + getFrom() + " At: " + getTimestamp();
    }

}