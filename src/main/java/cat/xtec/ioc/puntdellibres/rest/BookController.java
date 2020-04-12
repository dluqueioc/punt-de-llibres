package cat.xtec.ioc.puntdellibres.rest;

import java.security.Principal;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cat.xtec.ioc.puntdellibres.model.Author;
import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.Publisher;
import cat.xtec.ioc.puntdellibres.repository.AuthorRepository;
import cat.xtec.ioc.puntdellibres.repository.BookRepository;
import cat.xtec.ioc.puntdellibres.repository.PublisherRepository;
import cat.xtec.ioc.puntdellibres.repository.UserRepository;
import cat.xtec.ioc.puntdellibres.service.UserService;

@RestController
@RequestMapping("/api/books")
public class BookController {
   @Autowired
   private BookRepository bookRepository;
   @Autowired
   private PublisherRepository publisherRepository;
   @Autowired
   private AuthorRepository authorRepository;
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private UserService userService;

   @GetMapping(value = "", produces = MediaType.APPLICATION_JSON)
   public Iterable<Book> index(Principal user) {
      if (user == null) {
         return bookRepository.findAll();
      } else {
         String username = user.getName();
         Integer userId = userRepository.findByUsername(username).getId();
         return bookRepository.findAllWithUsers(userId);
      }
   }

   @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON)
   @ResponseBody
   public Iterable<Book> userBooks(@PathVariable("userId") String userId) {
      return bookRepository.findByUserId(Integer.parseInt(userId));
   }

   @PostMapping("")
   public Book newBook(@RequestBody Book newBook, Principal user) {
      Author author = authorRepository.findByName(newBook.getAuthorName());
      if (author == null) {
         author = new Author();
         author.setName(newBook.getAuthorName());
         authorRepository.save(author);
      }

      Publisher publisher = publisherRepository.findByName(newBook.getPublisherName());
      if (publisher == null) {
         publisher = new Publisher();
         publisher.setName(newBook.getPublisherName());
         publisherRepository.save(publisher);
      }

      String username = user.getName();
      Integer userId = userRepository.findByUsername(username).getId();
      newBook.setUserId(userId);

      newBook.setAuthorId(author.getId());
      newBook.setPublisherId(publisher.getId());
      return bookRepository.save(newBook);
   }

   @PutMapping(value = "/{bookId}/status", produces = MediaType.APPLICATION_JSON)
   public ResponseEntity<?> updateStatus(@PathVariable String bookId, Principal user) {
      Book book = bookRepository.findById(Integer.parseInt(bookId)).get();
      Integer status = book.getBookStatusId();

      if (book.getUserId() != userService.findMyId(user) || ! (status == 1 || status == 2)) {
         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      book.setBookStatusId(status == 1 ? 2 : 1);
      bookRepository.save(book);

      return new ResponseEntity<>(book, HttpStatus.OK);
   }
}