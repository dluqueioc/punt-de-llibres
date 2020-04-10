package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.UserApprovesExchange;

@RepositoryRestResource(exported = false)
public interface UserApprovesExchangeRepository extends CrudRepository<UserApprovesExchange, Integer> {
}