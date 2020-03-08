package cat.xtec.ioc.puntdellibres.service;

import cat.xtec.ioc.puntdellibres.model.Book;

public interface IBookService {
  public Iterable<Book> findAll();
  public Book save(Book book);
}