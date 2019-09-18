package com.reapal.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author jack-cooper
 * @version 1.0.0
 * @ClassName WebSocketConfig.java
 * @Description TODO
 * @createTime 2019年09月17日 15:15:00
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
