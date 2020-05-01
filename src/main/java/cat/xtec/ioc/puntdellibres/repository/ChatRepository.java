package cat.xtec.ioc.puntdellibres.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Chat;

@RepositoryRestResource(path = "chats", exported = false)
public interface ChatRepository extends CrudRepository<Chat, Integer>, ChatRepositoryCustom {
  List<Chat> findByUuid(UUID uuid);
}