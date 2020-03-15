package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Message;

@RepositoryRestResource(path = "messages")
public interface MessageRepository extends CrudRepository<Message, Integer> {
}