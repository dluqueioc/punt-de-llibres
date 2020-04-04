package cat.xtec.ioc.puntdellibres.model;

import java.io.Serializable;
import java.util.Objects;

public class UserWantsBookId implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer exchangeId;
  private Integer userId;
  private Integer bookId;

  public UserWantsBookId() {}

  public UserWantsBookId (Integer exchangeId, Integer userId, Integer bookId) {
      this.exchangeId = exchangeId;
      this.userId = userId;
      this.bookId = bookId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserWantsBookId userWantsBookId = (UserWantsBookId) o;

    return exchangeId.equals(userWantsBookId.exchangeId) &&
      userId.equals(userWantsBookId.userId) &&
      bookId.equals(userWantsBookId.bookId);
  }

  @Override
  public int hashCode() {
      return Objects.hash(userId, exchangeId);
  }
}