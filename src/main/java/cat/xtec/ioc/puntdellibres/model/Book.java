package cat.xtec.ioc.puntdellibres.model;

import java.time.LocalDateTime;

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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Data;

@Entity
@Table(name = "books")
@Data
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "isbn")
  @Size(min = 10, max = 20)
  private String isbn;

  @Column(name = "title")
  @NotNull
  @Size(min = 1, max = 100)
  private String title;

  // @Column(name = "author")
  // @NotNull
  // @Size(min = 1, max = 50)
  // private String author;

  //@Column(name = "publisher")
  //@Size(min = 1, max = 50)
  //private String publisher;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "author_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Author author;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "publisher_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Publisher publisher;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "genre_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Genre genre;
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "style_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Style style;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "language_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Language language;

  @Column(name = "edition")
  @Size(min = 1, max = 50)
  private String edition;

  @Column(name = "preservation")
  @Size(max = 100)
  private String preservation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "book_status_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private BookStatus bookStatus;

  @CreationTimestamp
  private LocalDateTime createdDate;
}