package cat.xtec.ioc.puntdellibres.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "chats")
@Data
public class Chat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JoinColumn(name = "user1_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  private User user1;

  @NotNull
  @Column(name = "user1_id")
  private Integer user1Id;

  @JoinColumn(name = "user2_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  private User user2;

  @NotNull
  @Column(name = "user2_id")
  private Integer user2Id;

  @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ChatMessage> messages;
}