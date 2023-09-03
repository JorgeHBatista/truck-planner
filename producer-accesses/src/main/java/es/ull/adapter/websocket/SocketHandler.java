package es.ull.adapter.websocket;

import es.ull.application.OutputPort;
import es.ull.domain.entity.Access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.net.InetSocketAddress;

@Component
public class SocketHandler extends TextWebSocketHandler implements OutputPort {

    private static final Logger logger = LoggerFactory.getLogger(SocketHandler.class);
    private List<WebSocketSession> sessions;

    public SocketHandler() {
        this.sessions = new ArrayList<>();
    }

    @Override
    public void sendEvent(String json)
            throws InterruptedException, IOException, ParseException {
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(json.getBytes("utf-8")));
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException, ParseException {
        logger.info("handleTextMessage");
        logger.info(message.toString());
        logger.info(message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable)
            throws Exception {
        InetSocketAddress clientAddress = session.getRemoteAddress();
        logger.info("Accepted connection from: {}:{}", 
                clientAddress.getHostString(),
                clientAddress.getPort());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        InetSocketAddress clientAddress = session.getRemoteAddress();
        HttpHeaders handshakeHeaders = session.getHandshakeHeaders();
        logger.info("Accepted connection from: {}:{}",
                clientAddress.getHostString(),
                clientAddress.getPort());
        logger.info("Client hostname: {}", clientAddress.getHostName());
        logger.info("Client IP: {}", clientAddress.getAddress().getHostAddress());
        logger.info("Client port: {}", clientAddress.getPort());
        logger.info("Session accepted protocols: {}", session.getAcceptedProtocol());
        logger.info("Session binary message size limit: {}",
                session.getBinaryMessageSizeLimit());
        logger.info("Session id: {}", session.getId());
        logger.info("Session text message size limit: {}",
                session.getTextMessageSizeLimit());
        logger.info("Session URI: {}", session.getUri().toString());
        logger.info("Handshake header: Accept {}", handshakeHeaders.toString());
        this.sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status)
            throws Exception {
        logger.info("Connection closed by {}:{}",
                session.getRemoteAddress().getHostString(),
                session.getRemoteAddress().getPort());
        this.sessions.remove(session);
        super.afterConnectionClosed(session, status);
    }
}
