package cat.xtec.ioc.puntdellibres.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.repository.BookRepository;

@Service
@Transactional
public class BookService implements IBookService {
  @Autowired
  private BookRepository bookRepository;

  public Iterable<Book> findAll() {
    return bookRepository.findAll();
  }

  public Book save(Book book) {
    return bookRepository.save(book);
  }
}