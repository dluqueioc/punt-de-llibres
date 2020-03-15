package cat.xtec.ioc.puntdellibres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.DateType;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "sender_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Exchange exchange;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "receiver_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User sender;

  @Column(name = "body")
  @NotNull
  @Size(min = 1, max = 500)
  private String body;

  @CreatedDate
  private DateType createdDate;
}