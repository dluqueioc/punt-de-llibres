package cat.xtec.ioc.puntdellibres.repository;

import java.security.Principal;

import cat.xtec.ioc.puntdellibres.model.Exchange;

public interface ExchangeRepositoryCustom {
  public Exchange create(Integer bookId, Principal user);
  public Iterable<Exchange> findMyExchanges(Principal user);
}