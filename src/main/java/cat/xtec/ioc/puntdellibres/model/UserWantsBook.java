package cat.xtec.ioc.puntdellibres.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Entity
@Table(name = "user_wants_book")
@IdClass(UserWantsBookId.class)
@Data
public class UserWantsBook {
  @JsonIgnore
  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "exchange_id", insertable = false, updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Exchange exchange;

  @Column(name = "exchange_id")
  private Integer exchangeId;

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private User user;

  @Column(name = "user_id")
  private Integer userId;

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "book_id", insertable = false, updatable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Book book;

  @Column(name = "book_id")
  private Integer bookId;
}