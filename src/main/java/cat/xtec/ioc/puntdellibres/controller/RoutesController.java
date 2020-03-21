package cat.xtec.ioc.puntdellibres.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.repository.BookRepository;
import cat.xtec.ioc.puntdellibres.repository.UserRepository;

@Controller
public class RoutesController {
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private UserRepository userRepository;

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

  @GetMapping("/llibres-disponibles")
  public String llibresDisponibles(final Model model, Principal user) {
    Iterable<Book> books = bookRepository.findAll();
    if (user == null) {
      model.addAttribute("books", bookRepository.findAll());
    } else {
      String username = user.getName();
      Integer userId = userRepository.findByUsername(username).getId();
      model.addAttribute("books", bookRepository.findAllButMine(userId));
    }

    return "llibres-disponibles";
  }

  @GetMapping("/afegir-llibre")
  public String afegirLlibre(final Model model) {
    return "afegir-llibre";
  }

}