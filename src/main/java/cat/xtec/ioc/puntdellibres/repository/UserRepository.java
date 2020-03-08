package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.CrudRepository;
import cat.xtec.ioc.puntdellibres.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}