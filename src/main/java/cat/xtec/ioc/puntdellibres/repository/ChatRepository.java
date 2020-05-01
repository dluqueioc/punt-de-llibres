package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Chat;

@RepositoryRestResource(path = "chats")
public interface ChatRepository extends CrudRepository<Chat, Integer>, ChatRepositoryCustom {
}