package cat.xtec.ioc.puntdellibres.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.repository.UserRepository;
import cat.xtec.ioc.puntdellibres.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
   @Autowired
   private BookService bookService;

   @Autowired
   private UserRepository userRepository;

   // @GetMapping
   // public Iterable<Book> index() {
   //  return bookService.findAll();
   // }

   @GetMapping("/prova")
   public Iterable<User> indexUser() {
    return userRepository.findAll();
   }
}