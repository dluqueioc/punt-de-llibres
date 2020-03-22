package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Publisher;

@RepositoryRestResource(exported = false)
public interface PublisherRepository extends CrudRepository<Publisher, Integer> {
  public Publisher findByName(String publisherName);
}