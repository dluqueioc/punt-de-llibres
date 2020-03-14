package cat.xtec.ioc.puntdellibres.model;

import java.io.Serializable;
import java.util.Objects;

public class UserRatingId implements Serializable {
  private static final long serialVersionUID = 1L;
  private User user;
  private User ratedBy;

  public UserRatingId() {}

  public UserRatingId (User user, User ratedBy) {
      this.user = user;
      this.ratedBy = ratedBy;
  }

  @Override
  public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      UserRatingId userRatingId = (UserRatingId) o;

      return user.getId().equals(userRatingId.user.getId()) && ratedBy.getId().equals(userRatingId.ratedBy.getId());
  }

  @Override
  public int hashCode() {
      return Objects.hash(user, ratedBy);
  }
}