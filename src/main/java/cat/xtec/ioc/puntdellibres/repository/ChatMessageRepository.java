package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.ChatMessage;

@RepositoryRestResource(path = "messages", exported = false)
public interface ChatMessageRepository extends CrudRepository<ChatMessage, Integer> {
}