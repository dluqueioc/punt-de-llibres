package cat.xtec.ioc.puntdellibres.repository;

import cat.xtec.ioc.puntdellibres.model.Chat;

public interface ChatRepositoryCustom {
  public Iterable<Chat> getByUserId(Integer userId);

  public Iterable<Chat> getByUserIds(Integer user1Id, Integer user2Id);
}