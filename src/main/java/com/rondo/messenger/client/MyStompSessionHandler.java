package com.rondo.messenger.client;

import com.rondo.messenger.domain.OutgoingMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

/**
 * This class is an implementation for <code>StompSessionHandlerAdapter</code>.
 * Once a connection is established, We subscribe to /topic/messages and
 * send a sample message to server.
 *
 * @author Kalyan
 */
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private Logger logger = LogManager.getLogger(MyStompSessionHandler.class);

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        logger.info("New session established : " + session.getSessionId());
        session.subscribe("/topic/greetings", this);
        logger.info("Subscribed to /topic/greetings");

        JSONObject json = new JSONObject();
        json.put("name", "Yetkin");

        session.send("/rondo-messenger/hello", json.toString());
        logger.info("Message sent to websocket server");
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        logger.error("Got an exception", exception);
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return OutgoingMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.info("hoop");
        logger.info("Received : " + payload.toString());
    }

    /**
     * A sample message instance.
     *
     * @return instance of <code>Message</code>
     */
//    private Message getSampleMessage() {
//        Message msg = new Message();
//        msg.setFrom("Nicky");
//        msg.setText("Howdy!!");
//        return msg;
//    }
}
