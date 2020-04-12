package cat.xtec.ioc.puntdellibres.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cat.xtec.ioc.puntdellibres.model.Book;

@RepositoryRestResource(path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Integer>, BookRepositoryCustom {
  @Query(
    value = "SELECT * FROM books LEFT JOIN users ON books.user_id = users.id WHERE books.user_id != ?1 AND books.book_status_id = 1",
    nativeQuery = true)
  public Iterable<Book> findAllWithUsers(Integer userId);
  
  public Iterable<Book> findByUserId(Integer userId);
}