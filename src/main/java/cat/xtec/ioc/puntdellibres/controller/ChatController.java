package cat.xtec.ioc.puntdellibres.controller;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import cat.xtec.ioc.puntdellibres.model.ChatMessage;
import cat.xtec.ioc.puntdellibres.model.Message;
import cat.xtec.ioc.puntdellibres.repository.ChatMessageRepository;
import cat.xtec.ioc.puntdellibres.repository.ChatRepository;
import cat.xtec.ioc.puntdellibres.service.UserService;

@Controller
public class ChatController {
  @Autowired
  private ChatRepository chatRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private ChatMessageRepository chatMessageRepository;

  @MessageMapping("/chat/{chatUuid}")
  @SendTo("/chat/messages/{chatUuid}")
  public Message send(Message message, @DestinationVariable String chatUuid, Principal user) throws Exception {
    String messageBody = message.getBody();
    ChatMessage chatMessage = new ChatMessage();
    chatMessage.setBody(messageBody);
    chatMessage.setChatId(chatRepository.findByUuid(UUID.fromString(chatUuid)).get(0).getId());
    Integer myUserId = userService.findMyId(user);
    chatMessage.setSenderId(myUserId);
    chatMessageRepository.save(chatMessage);
    return new Message(messageBody, myUserId);
  }

}