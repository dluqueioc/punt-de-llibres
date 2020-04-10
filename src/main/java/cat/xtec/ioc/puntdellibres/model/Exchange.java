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

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.type.DateType;
import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Entity
@Table(name = "exchanges")
@Data
public class Exchange {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @JoinColumn(name = "status_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = ExchangeStatus.class, fetch = FetchType.LAZY)
  @JsonIgnore
  private ExchangeStatus status;

  @Column(name = "status_id")
  private Integer statusId = 1;

  @OneToMany(
    mappedBy = "exchange",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<UserWantsBook> books;

  @OneToMany(
    mappedBy = "exchange",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<UserInExchange> users;

  @OneToMany(
    mappedBy = "exchange",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<UserApprovesExchange> approvals;

  @CreatedDate
  private DateType createdDate;
}