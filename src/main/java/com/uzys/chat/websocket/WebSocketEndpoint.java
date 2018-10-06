package com.uzys.chat.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ServerEndpoint(value = "/websocket")
public class WebSocketEndpoint {

    private static final Logger log = LogManager.getLogger(WebSocketEndpoint.class);

    public static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        log.trace("Websocket onOpen() called");
        log.debug("Connected session {}", session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.trace("Websocket onMessage() called");
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) {
        log.trace("Websocket onClose() called");
        log.debug("Closed session {}", session.getId());
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        log.trace("Websocket onError() called");
        log.warn("Error in socket connection {}", session.getId(), t);
    }

    private static void broadcast(String message) {
        for (Session session : sessions) {
            send(message, session);
        }
    }

    private static void send(String message, Session session) {
        if (session.isOpen()) {
            RemoteEndpoint.Async remoteEndpoint = session.getAsyncRemote();
            Future<Void> future = remoteEndpoint.sendText(message);
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Exception during sending of a message to client.", e);
            }
            log.trace("Message was sent to: {}", session);
        }
    }
}
