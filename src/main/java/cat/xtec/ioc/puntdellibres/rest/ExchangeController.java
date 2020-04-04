package cat.xtec.ioc.puntdellibres.rest;

import java.security.Principal;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cat.xtec.ioc.puntdellibres.model.Author;
import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.Exchange;
import cat.xtec.ioc.puntdellibres.model.Publisher;
import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.repository.AuthorRepository;
import cat.xtec.ioc.puntdellibres.repository.BookRepository;
import cat.xtec.ioc.puntdellibres.repository.ExchangeRepository;
import cat.xtec.ioc.puntdellibres.repository.PublisherRepository;
import cat.xtec.ioc.puntdellibres.repository.UserRepository;
// import cat.xtec.ioc.puntdellibres.service.BookService;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {
   @Autowired
   private ExchangeRepository exchangeRepository;

   @GetMapping(value = "", produces = MediaType.APPLICATION_JSON)
   public Iterable<Exchange> getAll() {
      return exchangeRepository.findAll();
   }

   @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON)
   public Iterable<Exchange> getMyExchanges(Principal user) {
      return exchangeRepository.findMyExchanges(user);
   }

   @PostMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON)
   public Exchange index(@PathVariable("bookId") String bookId, Principal user) {
      Exchange exchange = exchangeRepository.create(Integer.parseInt(bookId), user);
      return exchange;
   }
}