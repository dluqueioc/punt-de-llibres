package cat.xtec.ioc.puntdellibres.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.BookStatus;
import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.repository.BookRepository;
import cat.xtec.ioc.puntdellibres.repository.BookStatusRepository;

@Controller
public class HomeController {
  @Autowired
  private BookStatusRepository bookStatusRepository;
  @Autowired
  private BookRepository bookRepository;

  @GetMapping("/")
  public String home(final Model model) {
	  Iterable<Book> books = bookRepository.findAll();
	  model.addAttribute("books", books);
    return "home";
  }

  @GetMapping("/login")
  public String login(final Model model) {
    model.addAttribute("user", new User());
      return "login";
  }

  @PostMapping("/login")
  public String greetingSubmit(@ModelAttribute User user) {
    return "result";
  }

}