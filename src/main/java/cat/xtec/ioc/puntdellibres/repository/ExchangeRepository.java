package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Exchange;

@RepositoryRestResource(exported = false)
public interface ExchangeRepository extends PagingAndSortingRepository<Exchange, Integer> {
}