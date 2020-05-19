package cat.xtec.ioc.puntdellibres.controller;

import java.security.Principal;

import cat.xtec.ioc.puntdellibres.repository.*;
import cat.xtec.ioc.puntdellibres.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.Exchange;
import cat.xtec.ioc.puntdellibres.model.User;
//permet ordenar (sort) dades des del controller
import org.springframework.data.domain.Sort;

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
  @Autowired
  private ExchangeRepository exchangeRepository;
  @Autowired
  private UserService userService;

  @GetMapping(value={"", "/", "home"})
  public String home(final Model model) {
	//modificat per a que retorne els llibres a la home ordenats de més a menys recents
	Iterable<Book> books = bookRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
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
	 /* 
    if (user == null) {
      model.addAttribute("books", bookRepository.findAll());
    } else {
      String username = user.getName();
      Integer userId = userRepository.findByUsername(username).getId();
      model.addAttribute("books", bookRepository.findAllButMine(userId));
    }
    model.addAttribute("loggedIn", user != null);
    return "llibres-disponibles";
    */
	Boolean loggedIn = user != null;
		if (loggedIn) {
			model.addAttribute("myUserId", userService.findMyId(user));
		}
	    model.addAttribute("loggedIn", loggedIn);
	    return "llibres-disponibles";
  }

  @GetMapping("/afegir-llibre")
  public String afegirLlibre(final Model model) {
    model.addAttribute("publishers", publisherRepository.findAll());
    model.addAttribute("genres", genreRepository.findAll());
    model.addAttribute("languages", languageRepository.findAll());
    model.addAttribute("themes", themeRepository.findAll());
    return "afegir-llibre";
  }

  @GetMapping("/els-meus-intercanvis")
  public String elsMeusIntercanvis(final Model model, Principal user) {
    Iterable<Exchange> myExchanges = exchangeRepository.findMyExchanges(user);
    model.addAttribute("myExchanges", myExchanges);

    String username = user.getName();
    User me = userRepository.findByUsername(username);
    Integer myUserId = me.getId();
    model.addAttribute("myUserId", myUserId);

    return "els-meus-intercanvis";
  }

  @GetMapping("/els-meus-llibres")
  public String elsMeusLlibres(final Model model, Principal user) {
    model.addAttribute("myUserId", userService.findMyId(user));
    return "els-meus-llibres";
  }

  @GetMapping("/usuari")
  public String perfil(final Model model, Principal user) {
	  Integer id = userService.findMyId(user);
      model.addAttribute("userData", userRepository.findById(id).get());
      return "usuari";
  }

  @GetMapping("/modificar-dades")
  public String modificarDades(final Model model, Principal user) {
	  Integer id = userService.findMyId(user);
      model.addAttribute("user", userRepository.findById(id).get());
      return "modificar-dades";
  }

  //mètodes per gestionar les peticions a les pàgines legals (estàtiques)
  @GetMapping("/privacitat")
  public String privacitat(final Model model) {
      return "privacitat";
  }
  @GetMapping("/condicions-us")
  public String condicions(final Model model) {
      return "condicions-us";
  }
  @GetMapping("/cookies")
  public String cookies(final Model model) {
    return "cookies";
  }
}