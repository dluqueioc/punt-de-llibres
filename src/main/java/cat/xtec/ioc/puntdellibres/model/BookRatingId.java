package cat.xtec.ioc.puntdellibres.model;

import java.io.Serializable;
import java.util.Objects;

public class BookRatingId implements Serializable {
  private static final long serialVersionUID = 1L;
  private User user;
  private Book book;

  public BookRatingId() {}

  public BookRatingId (User user, Book book) {
      this.user = user;
      this.book = book;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      BookRatingId bookRatingId = (BookRatingId) o;

      return user.getId().equals(bookRatingId.user.getId()) && book.getId().equals(bookRatingId.book.getId());
  }

  @Override
  public int hashCode() {
      return Objects.hash(user, book);
  }
}