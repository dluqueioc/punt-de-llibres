package cat.xtec.ioc.puntdellibres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.repository.UserRepository;
import cat.xtec.ioc.puntdellibres.service.BookService;

@Controller
public class HomeController {

  @Autowired
  private BookService bookService;
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/")
  public String home(final Model model) {
    User user = new User();
    user.setUsername("john");
    user.setEmail("email");
    user.setName("name");
    user.setLastName("last");
    user.setLocation("11111");
    userRepository.save(user);

    Book book = new Book();
    book.setTitle("1899");
    book.setUser(user);
    bookService.save(book);
    final Iterable<Book> books = bookService.findAll();
    books.forEach(b -> System.out.println(b));

    model.addAttribute("books", books);
    return "home";
  }

}