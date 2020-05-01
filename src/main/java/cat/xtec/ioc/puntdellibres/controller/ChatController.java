package cat.xtec.ioc.puntdellibres.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import cat.xtec.ioc.puntdellibres.model.Message;

@Controller
public class ChatController {

  @MessageMapping("/chat/{chatId}")
  @SendTo("/chat/messages/{chatId}")
  public Message greeting(Message message, @DestinationVariable String chatId) throws Exception {
    System.out.println(chatId);
    return new Message("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }

}