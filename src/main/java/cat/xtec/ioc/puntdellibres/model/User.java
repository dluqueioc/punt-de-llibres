package cat.xtec.ioc.puntdellibres.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
  @Transient
  private List<GrantedAuthority> roles;

  public User() {}

  // public User(String username, String password, Collection<Role> roles) {
  //   this.username = username;
  //   this.password = password;
  //   this.roles = roles;
  // }

  public User(String username, String password, List<GrantedAuthority> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "username", unique = true)
  @NotNull
  @Size(min = 3, max = 20)
  private String username;

  // @ManyToMany
  // @JoinTable(
  //   name = "user_roles",
  //   joinColumns = @JoinColumn(
  //   name = "user_id", referencedColumnName = "id"),
  //   inverseJoinColumns = @JoinColumn(
  //   name = "role_id", referencedColumnName = "id"))
  // private Collection<Role> roles;

  @Column(name = "email", unique = true)
  @NotNull
  @Size(min = 3, max = 50)
  private String email;

  @Column(name = "password")
  @NotNull
  @Size(min = 3, max = 100)
  @JsonIgnore
  private String password = "prova";

  @Column(name = "name")
  @NotNull
  @Size(min = 3, max = 50)
  private String name;

  @Column(name = "last_name")
  @NotNull
  @Size(min = 3, max = 50)
  private String lastName;

  @Column(name = "location")
  @NotNull
  @Size(min = 3, max = 100)
  private String location;

  @Column(name = "geo_location")
  @Size(min = 3, max = 100)
  private String geoLocation;

  @Column(name = "profile")
  @Size(min = 3, max = 500)
  private String profile;

  @Column(name = "rating")
  private float rating;

  @ManyToMany
  @JoinTable(
    name = "user_likes_author",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id")
  )
  private Set<Author> authorsLiked;

  @ManyToMany
  @JoinTable(
    name = "user_likes_genre",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genresLiked;

  @ManyToMany
  @JoinTable(
    name = "user_likes_language",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "language_id"))
  private Set<Language> languagesLiked;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Book> books;

  @OneToMany(
    mappedBy = "userId",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<UserInExchange> exchanges;

  @CreationTimestamp
  private LocalDateTime createdDate;
}