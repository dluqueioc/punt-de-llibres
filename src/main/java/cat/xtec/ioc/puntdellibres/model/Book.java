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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.CreationTimestamp;
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

  @JoinColumn(name = "author_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = Author.class, fetch = FetchType.LAZY)
  private Author author;

  @Column(name = "author_id")
  private Integer authorId;

  @JoinColumn(name = "publisher_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = Publisher.class, fetch = FetchType.LAZY)
  private Publisher publisher;

  @Column(name = "publisher_id")
  private Integer publisherId;

  @JoinColumn(name = "genre_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = Genre.class, fetch = FetchType.LAZY)
  private Genre genre;

  @Column(name = "genre_id")
  private Integer genreId;

  @JoinColumn(name = "theme_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = Theme.class, fetch = FetchType.LAZY)
  private Genre theme;

  @Column(name = "theme_id")
  private Integer themeId;

  @JoinColumn(name = "language_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = Language.class, fetch = FetchType.LAZY)
  private Language language;

  @Column(name = "language_id")
  private Integer languageId;

  @Column(name = "edition")
  @Size(min = 1, max = 50)
  private String edition;

  @Column(name = "preservation")
  @Size(max = 100)
  private String preservation;

  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  private User user;

  @JsonIgnore
  @Column(name = "user_id")
  private Integer userId;

  @JoinColumn(name = "book_status_id", insertable = false, updatable = false)
  @ManyToOne(targetEntity = BookStatus.class, fetch = FetchType.LAZY)
  private BookStatus bookStatus;

  @Column(name = "book_status_id")
  private Integer bookStatusId = 1;

  @CreationTimestamp
  private LocalDateTime createdDate;
}