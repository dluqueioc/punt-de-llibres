package cat.xtec.ioc.puntdellibres.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.Principal;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
   public ResponseEntity<Object> create(@RequestParam("title") String title, @RequestParam("isbn") String isbn,
         @RequestParam("authorName") String authorName, @RequestParam("publisherName") String publisherName,
         @RequestParam("genreId") String genreId, @RequestParam("themeId") String themeId,
         @RequestParam("languageId") String languageId, @RequestParam("preservation") String preservation,
         @RequestParam("edition") String edition, @RequestParam("file") MultipartFile[] files, Principal user)
         throws IOException {
      // TODO : Move to service

      MultipartFile file = files[0];
      String fileName = files[0].getOriginalFilename();

      Book newBook = new Book();
      newBook.setTitle(title);
      if (!isbn.trim().isEmpty()) {
         newBook.setIsbn(isbn);
      }
      newBook.setGenreId(Integer.parseInt(genreId));
      newBook.setThemeId(Integer.parseInt(themeId));
      newBook.setLanguageId(Integer.parseInt(languageId));
      if (!preservation.isEmpty()) {
         newBook.setPreservation(preservation);
      }
      if (!edition.trim().isEmpty()) {
         newBook.setEdition(edition);
      }

      if (!fileName.isEmpty()) {
         File newFile = new File("./src/main/resources/static/img/covers", fileName);
         String mimeType = Files.probeContentType(newFile.toPath());

         if (mimeType.equals("image/jpeg")) {
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
            outputStream.write(file.getBytes());
            outputStream.flush();
            outputStream.close();
            String uniqueFileName = UUID.randomUUID() + ".jpg";
            newFile.renameTo(new File("./src/main/resources/static/img/covers", uniqueFileName));
            newBook.setCover(uniqueFileName);
         }
      }

      Author author = authorRepository.findByName(authorName);
      if (author == null) {
         author = new Author();
         author.setName(authorName);
         authorRepository.save(author);
      }
      newBook.setAuthorId(author.getId());

      Publisher publisher = publisherRepository.findByName(publisherName);
      if (publisher == null) {
         publisher = new Publisher();
         publisher.setName(publisherName);
         publisherRepository.save(publisher);
      }
      newBook.setPublisherId(publisher.getId());

      String username = user.getName();
      Integer userId = userRepository.findByUsername(username).getId();
      newBook.setUserId(userId);

      bookRepository.save(newBook);

      return new ResponseEntity<>("Llibre creat correctament", HttpStatus.OK);
   }

   @PutMapping(value = "/{bookId}/status", produces = MediaType.APPLICATION_JSON)
   public ResponseEntity<?> updateStatus(@PathVariable String bookId, Principal user) {
      Book book = bookRepository.findById(Integer.parseInt(bookId)).get();
      Integer status = book.getBookStatusId();

      if (book.getUserId() != userService.findMyId(user) || !(status == 1 || status == 2)) {
         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      book.setBookStatusId(status == 1 ? 2 : 1);
      bookRepository.save(book);

      return new ResponseEntity<>(book, HttpStatus.OK);
   }

   @DeleteMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON)
   public ResponseEntity<?> destroy(@PathVariable String bookId, Principal user) {
      Book book = bookRepository.findById(Integer.parseInt(bookId)).get();
      Integer status = book.getBookStatusId();

      if (book.getUserId() != userService.findMyId(user) || !(status == 1 || status == 2)) {
         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      bookRepository.delete(book);

      return new ResponseEntity<>(book, HttpStatus.OK);
   }
}