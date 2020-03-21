package cat.xtec.ioc.puntdellibres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.repository.BookRepository;

@Controller
public class RoutesController {
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

  @GetMapping("/llibres-disponibles")
  public String greetingSubmit(final Model model) {
    model.addAttribute("books", bookRepository.findAll());
    return "llibres-disponibles";
  }

  @GetMapping("/afegir-llibre")
  public String afegirLlibre(final Model model) {
    return "afegir-llibre";
  }

}