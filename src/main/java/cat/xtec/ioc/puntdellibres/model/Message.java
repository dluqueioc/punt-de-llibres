package cat.xtec.ioc.puntdellibres.model;

import lombok.Data;

@Data
public class Message {
  private String name;

  public Message() {
  }

  public Message(String name) {
    this.name = name;
  }
}