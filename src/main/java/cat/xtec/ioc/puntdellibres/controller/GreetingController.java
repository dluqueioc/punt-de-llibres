package cat.xtec.ioc.puntdellibres.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import cat.xtec.ioc.puntdellibres.model.Greeting;
import cat.xtec.ioc.puntdellibres.model.Message;

@Controller
public class GreetingController {

  @MessageMapping("/hello/{chatId}")
  @SendTo("/topic/greetings")
  public Greeting greeting(Message message, @DestinationVariable String chatId) throws Exception {
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }

}