package cat.xtec.ioc.puntdellibres.controller;

import java.security.Principal;

import cat.xtec.ioc.puntdellibres.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.User;

@Controller
public class RoutesController {
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private GenreRepository genreRepository;
  @Autowired
  private ThemeRepository themeRepository;
  @Autowired
  private LanguageRepository languageRepository;
  @Autowired
  private PublisherRepository publisherRepository;

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
    if (user == null) {
      model.addAttribute("books", bookRepository.findAll());
    } else {
      String username = user.getName();
      Integer userId = userRepository.findByUsername(username).getId();
      model.addAttribute("books", bookRepository.findAllButMine(userId));
    }
    model.addAttribute("loggedIn", user != null);
    return "llibres-disponibles";
  }

  @GetMapping("/afegir-llibre")
  public String afegirLlibre(final Model model) {
    //model.addAttribute("publishers", publisherRepository.findAll());
    model.addAttribute("genres", genreRepository.findAll());
    model.addAttribute("languages", languageRepository.findAll());
    model.addAttribute("themes", themeRepository.findAll());
    return "afegir-llibre";
  }
}