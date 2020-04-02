package cat.xtec.ioc.puntdellibres.repository;

import cat.xtec.ioc.puntdellibres.model.Exchange;

public interface ExchangeRepositoryCustom {
  public Exchange create(Integer bookId);
}