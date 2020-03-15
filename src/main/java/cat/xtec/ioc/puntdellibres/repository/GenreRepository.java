package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Genre;

@RepositoryRestResource(path = "genres")
public interface GenreRepository extends PagingAndSortingRepository<Genre, Integer> {
}