package cat.xtec.ioc.puntdellibres.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.type.DateType;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

// @Entity
// @Table(name = "exchange_details")
// @Data
public class ExchangeDetails {
  // @ManyToOne(fetch = FetchType.LAZY, optional = false)
  // @JoinColumn(name = "status_id", nullable = false)
  // @OnDelete(action = OnDeleteAction.CASCADE)
  // private ExchangeStatus status;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @CreatedDate
  private DateType createdDate;
}