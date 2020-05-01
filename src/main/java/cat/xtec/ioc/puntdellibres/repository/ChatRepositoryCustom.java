package cat.xtec.ioc.puntdellibres.repository;

import cat.xtec.ioc.puntdellibres.model.Chat;

public interface ChatRepositoryCustom {
  public Iterable<Chat> getByUserId(Integer userId);
}