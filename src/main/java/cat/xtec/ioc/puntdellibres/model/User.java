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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

  public User() {
  }

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
  @Size(min = 3, max = 20, message = "El nom d'usuari ha de tenir entre {min} i {max} caràcters")
  private String username;

  @Column(name = "email", unique = true)
  @NotNull
  @Email(message = "El correu electrònic ha de ser vàlid")
  @Size(min = 3, max = 50, message = "El correu electrònic ha de tenir entre 3 i 50 caràcters")
  private String email;

  @Column(name = "password")
  @NotNull
  @Size(min = 3, max = 100, message = "La contrasenya ha de tenir entre 3 i 100 caràcters")
  @JsonIgnore
  private String password;

  @Column(name = "name")
  @Size(min = 3, max = 50)
  private String name;

  @Column(name = "last_name")
  @Size(min = 3, max = 50)
  private String lastName;

  @Column(name = "location")
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
  @JoinTable(name = "user_likes_author", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
  private Set<Author> authorsLiked;

  @ManyToMany
  @JoinTable(name = "user_likes_genre", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
  private Set<Genre> genresLiked;

  @ManyToMany
  @JoinTable(name = "user_likes_language", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "language_id"))
  private Set<Language> languagesLiked;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Book> books;

  @JsonBackReference
  @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserInExchange> exchanges;

  @Column(name = "avatar")
  private String avatar = "default-avatar.png";

  @CreationTimestamp
  private LocalDateTime createdDate;
}