package nc.oliweb.jdbctemplate.web.websocket;

import nc.oliweb.jdbctemplate.web.websocket.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;
import java.time.Instant;

import static nc.oliweb.jdbctemplate.config.WebsocketConfiguration.MESSAGE;

@Controller
public class MessageService implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    private final SimpMessageSendingOperations messagingTemplate;

    public MessageService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/topic/chat")
    @SendTo("/topic/messages")
    public MessageDTO sendMessage(@Payload MessageDTO messageDTO, StompHeaderAccessor stompHeaderAccessor, Principal principal) {
        messageDTO.setFrom(principal.getName());
        messageDTO.setText(messageDTO.getText());
        messageDTO.setTime(Instant.now());
        log.debug("Sending message data {}", messageDTO);
        return messageDTO;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFrom(event.getSessionId());
        messageDTO.setText(event.getSessionId() + " a quitté le groupe.");
        messageDTO.setTime(Instant.now());
        messagingTemplate.convertAndSend("/topic/messages", messageDTO);
    }
}
