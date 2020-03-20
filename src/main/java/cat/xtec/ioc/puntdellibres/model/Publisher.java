package cat.xtec.ioc.puntdellibres.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "publishers")
@Data
public class Publisher {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  @NotNull
  @Size(min = 3, max = 50)
  private String name;

  @ManyToMany(mappedBy = "authorsLiked")
  private Set<User> likedBy;

  @OneToMany(
    mappedBy = "publisher",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Book> books;
}