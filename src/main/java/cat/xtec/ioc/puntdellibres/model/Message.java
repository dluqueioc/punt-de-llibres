package cat.xtec.ioc.puntdellibres.model;

import lombok.Data;

@Data
public class Message {
  private String body;
  private Integer senderId;

  public Message() {
  }

  public Message(String body, Integer senderId) {
    this.body = body;
    this.senderId = senderId;
  }
}