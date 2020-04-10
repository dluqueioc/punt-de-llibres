package cat.xtec.ioc.puntdellibres.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.UserInExchange;
import cat.xtec.ioc.puntdellibres.model.UserInExchangeId;

@RepositoryRestResource(path = "user-in-exchange", exported = false)
public interface UserInExchangeRepository extends CrudRepository<UserInExchange, UserInExchangeId> {
  public List<UserInExchange> findByExchangeId(Integer exchangeId);
}