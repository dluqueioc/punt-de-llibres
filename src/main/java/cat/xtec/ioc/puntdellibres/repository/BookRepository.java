package cat.xtec.ioc.puntdellibres.repository;

// import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import cat.xtec.ioc.puntdellibres.model.Book;

@RepositoryRestResource(path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
}