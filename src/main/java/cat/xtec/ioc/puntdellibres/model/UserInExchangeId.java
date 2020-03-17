package cat.xtec.ioc.puntdellibres.model;

import java.io.Serializable;
import java.util.Objects;

public class UserInExchangeId implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long userId;
  private Long exchangeId;

  public UserInExchangeId() {}

  public UserInExchangeId (Long userId, Long exchangeId) {
      this.userId = userId;
      this.exchangeId = exchangeId;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      UserInExchangeId userInExchangeId = (UserInExchangeId) o;

      return userId.equals(userInExchangeId.userId) && exchangeId.equals(userInExchangeId.exchangeId);
  }

  @Override
  public int hashCode() {
      return Objects.hash(userId, exchangeId);
  }
}