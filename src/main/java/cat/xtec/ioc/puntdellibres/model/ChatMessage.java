package cat.xtec.ioc.puntdellibres.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Table(name = "chat_messages")
@Data
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JsonIgnore
  @JoinColumn(name = "chat_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = Chat.class, fetch = FetchType.LAZY)
  private Chat chat;

  @Column(name = "chat_id")
  private Integer chatId;

  @JoinColumn(name = "sender_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
  private User sender;

  @Column(name = "sender_id")
  private Integer senderId;

  @Column(name = "body", columnDefinition="TEXT")
  @NotNull
  @NotBlank
  private String body;

  @CreationTimestamp
  private LocalDateTime createdDate;
}