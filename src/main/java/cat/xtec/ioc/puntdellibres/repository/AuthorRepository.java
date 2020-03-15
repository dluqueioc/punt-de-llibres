package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Author;

@RepositoryRestResource(path = "authors")
public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer> {
}