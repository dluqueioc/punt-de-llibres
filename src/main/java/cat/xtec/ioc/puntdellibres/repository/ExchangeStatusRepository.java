package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.ExchangeStatus;

@RepositoryRestResource(exported = false)
public interface ExchangeStatusRepository extends PagingAndSortingRepository<ExchangeStatus, Integer> {
}