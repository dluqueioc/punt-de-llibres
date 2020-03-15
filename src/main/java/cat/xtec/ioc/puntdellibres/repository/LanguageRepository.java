package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Language;

@RepositoryRestResource(path = "languages", exported = false)
public interface LanguageRepository extends PagingAndSortingRepository<Language, Integer> {
}