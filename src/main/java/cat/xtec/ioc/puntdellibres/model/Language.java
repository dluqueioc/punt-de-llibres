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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name = "languages")
@Data
public class Language {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "name")
  @NotNull
  @Size(min = 3, max = 100)
  private String name;

  @ManyToMany(mappedBy = "languagesLiked")
  private Set<User> likedBy;

  @JsonIgnore
  @OneToMany(
    mappedBy = "language",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Book> books;
}