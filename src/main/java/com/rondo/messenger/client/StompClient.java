package com.rondo.messenger.client;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public class StompClient {

    public static void main(String[] args) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        SockJsClient sockJsClient = new SockJsClient(List.of(new WebSocketTransport(webSocketClient)));
        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        StompSession session;
        try {
            session = stompClient
                    .connect(
                            "http://localhost:" + 8080 + "/rondo-messenger-ws",
                            new StompSessionHandlerAdapter() {
                                @Override
                                public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
                                    session.subscribe("/topic/greetings", this);
                                    JSONObject json = new JSONObject();
                                    json.put("name", "Yetkin");
                                    session.send("/rondo-messenger/hello", json.toString());
                                }

                                @Override
                                public void handleFrame(StompHeaders headers, Object payload) {
                                    log.info("Received : " + payload.toString());
                                }
                            })
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
