package cat.xtec.ioc.puntdellibres.rest;

import java.security.Principal;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.Exchange;
import cat.xtec.ioc.puntdellibres.model.UserApprovesExchange;
import cat.xtec.ioc.puntdellibres.model.UserInExchange;
import cat.xtec.ioc.puntdellibres.model.UserWantsBook;
import cat.xtec.ioc.puntdellibres.repository.BookRepository;
import cat.xtec.ioc.puntdellibres.repository.ExchangeRepository;
import cat.xtec.ioc.puntdellibres.repository.UserApprovesExchangeRepository;
import cat.xtec.ioc.puntdellibres.repository.UserRepository;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangeController {
   @Autowired
   private ExchangeRepository exchangeRepository;

   @Autowired
   private UserApprovesExchangeRepository userApprovesExchangeRepository;

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private BookRepository bookRepository;

   @GetMapping(value = "", produces = MediaType.APPLICATION_JSON)
   public Iterable<Exchange> getAll() {
      return exchangeRepository.findAll();
   }

   @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON)
   public Iterable<Exchange> getMyExchanges(Principal user) {
      return exchangeRepository.findMyExchanges(user);
   }

   @PostMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON)
   public String create(@PathVariable("bookId") String bookId, Principal user) {
      exchangeRepository.create(Integer.parseInt(bookId), user);
      return bookId;
   }

   @PostMapping(value = "/{exchangeId}/approve", produces = MediaType.APPLICATION_JSON)
   public Exchange approve(@PathVariable("exchangeId") String exchangeId, @RequestParam(required = true) Boolean approve,
         Principal user) {
      // TODO: validation

      UserApprovesExchange approval = new UserApprovesExchange();
      approval.setExchangeId(Integer.parseInt(exchangeId));
      approval.setApproved(approve);
      String username = user.getName();
      Integer userId = userRepository.findByUsername(username).getId();
      approval.setUserId(userId);
      userApprovesExchangeRepository.save(approval);

      Exchange exchange = exchangeRepository.findById(Integer.parseInt(exchangeId)).get();
      if (!approve) {
         exchange.setStatusId(5);
      } else {
         if (exchange.getApprovals().size() == 1) {
            exchange.setStatusId(3);
         } else {
            exchange.setStatusId(4);
            List<UserWantsBook> booksInExchange = exchange.getBooks();
            for (UserWantsBook bookWanted : booksInExchange) {
               Book book = bookWanted.getBook();
               book.setBookStatusId(3);
            }
         }
      }

      exchangeRepository.save(exchange);

      return exchange;
   }

   @PostMapping(value = "/{exchangeId}/close", produces = MediaType.APPLICATION_JSON)
   public Exchange close(@PathVariable("exchangeId") String exchangeId, @RequestParam(required = true) Boolean close, Principal user) {
      // TODO: validation
      Exchange exchange = exchangeRepository.findById(Integer.parseInt(exchangeId)).get();
      exchange.setStatusId(close ? 6 : 5);
      exchangeRepository.save(exchange);

      if (close) {
         String username = user.getName();
         Integer myUserId = userRepository.findByUsername(username).getId();
         List<UserInExchange> users = exchange.getUsers();

         Integer otherUserId = null;
         for (UserInExchange userInExchange : users) {
            if (userInExchange.getUserId() != myUserId) {
               otherUserId = userInExchange.getUserId();
               break;
            }
         }
         List<UserWantsBook> books = exchange.getBooks();
         for (UserWantsBook bookInExchange : books) {
            Book book = bookRepository.findById(bookInExchange.getBookId()).get();
            Integer previousOwner = book.getUserId();
            book.setUserId(previousOwner == myUserId ? otherUserId : myUserId);
            book.setBookStatusId(2);
            bookRepository.save(book);
         }
      }

      return exchange;
   }
}