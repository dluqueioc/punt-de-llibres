package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.User;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Integer> {
  User findByUsername(String username);
  User findByEmail(String email);
}