package es.ull.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import es.ull.adapter.websocket.SocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    @Autowired
    private SocketHandler socketHandler;

    public WebSocketConfig() {
        logger.info("WebSocket Server Created");
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        logger.info("registerWebSocketHandlers");
        registry
                .addHandler(this.socketHandler, "/")
                .setAllowedOrigins("*");
        logger.info(registry.toString());
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        logger.info("createWebSocketContainer");
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
}
