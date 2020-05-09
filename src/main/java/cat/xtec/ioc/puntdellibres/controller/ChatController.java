package cat.xtec.ioc.puntdellibres.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.UUID;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cat.xtec.ioc.puntdellibres.model.Chat;
import cat.xtec.ioc.puntdellibres.model.ChatMessage;
import cat.xtec.ioc.puntdellibres.model.Exchange;
import cat.xtec.ioc.puntdellibres.model.Message;
import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.model.UserInExchange;
import cat.xtec.ioc.puntdellibres.repository.ChatMessageRepository;
import cat.xtec.ioc.puntdellibres.repository.ChatRepository;
import cat.xtec.ioc.puntdellibres.repository.ExchangeRepository;
import cat.xtec.ioc.puntdellibres.service.UserService;

@Controller
public class ChatController {
  @Autowired
  private ChatRepository chatRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private ChatMessageRepository chatMessageRepository;
  @Autowired
  private ExchangeRepository exchangeRepository;

  @GetMapping("/les-meves-converses")
  public String lesMevesConverses(final Model model, Principal user) throws JsonProcessingException {
    Integer myUserId = userService.findMyId(user);
    model.addAttribute("myUserId", myUserId);
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    String myChats = mapper.writeValueAsString(chatRepository.getByUserId(myUserId));
    model.addAttribute("myChats", myChats);
    return "les-meves-converses";
  }

  @PostMapping(value = "/chats", produces = MediaType.APPLICATION_JSON)
  @ResponseBody
  public Integer create(@QueryParam("exchangeId") String exchangeId, Principal user) throws Exception {
    Integer myUserId = userService.findMyId(user);
    Exchange exchange = exchangeRepository.findById(Integer.parseInt(exchangeId)).get();
    Integer chatId = null;
    Boolean exchangeIsMine = false;
    Integer otherUserId = null;

    for (UserInExchange userInExchange : exchange.getUsers()) {
      if (userInExchange.getUserId() == myUserId) {
        exchangeIsMine = true;
      } else {
        otherUserId = userInExchange.getUserId();
      }
    }
    if (!exchangeIsMine) {
      throw new Exception();
    }

    Iterator<Chat> chatIterator = chatRepository.getByUserIds(myUserId, otherUserId).iterator();
    if (chatIterator.hasNext()) {
      chatId = chatIterator.next().getId();
    } else {
      if (exchange.getStatus().getName().equals("aprovat")) {
        Chat chat = new Chat();
        chat.setUser1Id(myUserId);
        chat.setUser2Id(otherUserId);
        chatRepository.save(chat);
        chatId = chat.getId();
      }
    }

    return chatId;
  }

  @GetMapping("/conversa/{chatId}")
  public String conversa(final Model model, @PathVariable("chatId") String chatId, Principal user) throws Exception {
    Integer myUserId = userService.findMyId(user);
    Chat chat = chatRepository.findById(Integer.parseInt(chatId)).get();
    if (!(chat.getUser1Id().equals(myUserId) || chat.getUser2Id().equals(myUserId))) {
      throw new Exception();
    }
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    String messages = mapper.writeValueAsString(chat.getMessages());
    User otherUser = chat.getUser1Id() == myUserId ? chat.getUser2() : chat.getUser1();
    model.addAttribute("otherUserAvatar", otherUser.getAvatar());
    model.addAttribute("otherUserUsername", otherUser.getUsername());
    model.addAttribute("otherUserId", otherUser.getId());
    model.addAttribute("uuid", chat.getUuid().toString());
    model.addAttribute("myUserId", myUserId);
    model.addAttribute("messages", messages);
    return "conversa";
  }

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