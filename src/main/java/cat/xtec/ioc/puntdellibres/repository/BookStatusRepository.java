package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.BookStatus;

@RepositoryRestResource(path = "book_statuses", exported = false)
public interface BookStatusRepository extends CrudRepository<BookStatus, Integer> {
}