package cat.xtec.ioc.puntdellibres.model;

import java.io.Serializable;
import java.util.Objects;

public class UserApprovesExchangeId implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer exchangeId;
  private Integer userId;

  public UserApprovesExchangeId() {}

  public UserApprovesExchangeId (Integer exchangeId, Integer userId) {
      this.exchangeId = exchangeId;
      this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserApprovesExchangeId userApprovesExchangeId = (UserApprovesExchangeId) o;

    return exchangeId.equals(userApprovesExchangeId.exchangeId) &&
        userId.equals(userApprovesExchangeId.userId);
  }

  @Override
  public int hashCode() {
      return Objects.hash(userId, exchangeId);
  }
}