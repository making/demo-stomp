package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import java.util.List;

@EnableAutoConfiguration
@ComponentScan
@Controller
public class App {
    @Configuration
    @EnableWebSocketMessageBroker
    static class Config extends AbstractWebSocketMessageBrokerConfigurer {
        @Override
        public void configureMessageBroker(MessageBrokerRegistry config) {
            config.enableSimpleBroker("/topic");
            config.setApplicationDestinationPrefixes("/app");
        }

        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint("/hello").withSockJS();
        }

        @Override
        public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
            messageConverters.add(new StringMessageConverter());
            return true;
        }
    }

    @MessageMapping("/hello")
    String hello(String message) {
        return "Hello " + message;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}