package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Theme;

@RepositoryRestResource(path = "themes")
public interface ThemeRepository extends PagingAndSortingRepository<Theme, Integer> {
}