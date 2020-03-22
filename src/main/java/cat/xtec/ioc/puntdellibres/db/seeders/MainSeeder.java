package cat.xtec.ioc.puntdellibres.db.seeders;

import java.util.ArrayList;
import java.util.List;

import com.github.javafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;

import cat.xtec.ioc.puntdellibres.model.Author;
import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.BookStatus;
import cat.xtec.ioc.puntdellibres.model.Exchange;
import cat.xtec.ioc.puntdellibres.model.ExchangeStatus;
import cat.xtec.ioc.puntdellibres.model.Genre;
import cat.xtec.ioc.puntdellibres.model.Language;
import cat.xtec.ioc.puntdellibres.model.Message;
import cat.xtec.ioc.puntdellibres.model.Publisher;
import cat.xtec.ioc.puntdellibres.model.Theme;
import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.repository.AuthorRepository;
import cat.xtec.ioc.puntdellibres.repository.BookRepository;
import cat.xtec.ioc.puntdellibres.repository.BookStatusRepository;
import cat.xtec.ioc.puntdellibres.repository.ExchangeRepository;
import cat.xtec.ioc.puntdellibres.repository.ExchangeStatusRepository;
import cat.xtec.ioc.puntdellibres.repository.GenreRepository;
import cat.xtec.ioc.puntdellibres.repository.LanguageRepository;
import cat.xtec.ioc.puntdellibres.repository.MessageRepository;
import cat.xtec.ioc.puntdellibres.repository.PublisherRepository;
import cat.xtec.ioc.puntdellibres.repository.ThemeRepository;
import cat.xtec.ioc.puntdellibres.repository.UserRepository;
import lombok.Getter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MainSeeder implements CommandLineRunner {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthorRepository authorRepository;
  @Autowired
  private BookStatusRepository bookStatusRepository;
  @Autowired
  private GenreRepository genreRepository;
  @Autowired
  private ThemeRepository themeRepository;
  @Autowired
  private LanguageRepository languageRepository;
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private ExchangeStatusRepository exchangeStatusRepository;
  @Autowired
  private ExchangeRepository exchangeRepository;
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private PublisherRepository publisherRepository;

  private final Faker faker = new Faker();

  private @Getter final List<Book> books = new ArrayList<>();

  @Value("${spring.profiles.active:Unknown}")
  private String activeProfile;

  private boolean persist = false;

  @Override
  public void run(final String... args) throws Exception {
    if (activeProfile.equals("Unknown")) {
      persist = true;
    }
    seedUsersTable();
    seedAuthorsTable(10);
    seedGenresTable();
    seedLanguagesTable();
    seedThemesTable();
    seedBookStatusesTable();
    seedPublishersTable();
    seedBooksTable();
    seedExchangeStatusesTable();
    seedExchangesTable();
    seedMessagesTable();
  }

  private void seedUsersTable() {
    final User user1 = new User();
    user1.setPassword(new BCryptPasswordEncoder().encode("test123"));
    user1.setUsername("john");
    user1.setEmail("john@test.com");
    user1.setName("john");
    user1.setLastName("doe");
    user1.setLocation("11111");
    userRepository.save(user1);

    final User user2 = new User();
    user2.setPassword(new BCryptPasswordEncoder().encode("test123"));
    user2.setUsername("jane");
    user2.setEmail("jane@test.com");
    user2.setName("jane");
    user2.setLastName("doe");
    user2.setLocation("11111");
    userRepository.save(user2);
  }

  private void seedAuthorsTable(final int quantity) {
    for (int i = 1; i <= quantity; i++) {
      final Author author = new Author();
      author.setName(faker.name().firstName() + " " + faker.name().lastName());
      authorRepository.save(author);
    }
  }

  private void seedGenresTable() {
    final String[] genres = { "Narrativa", "Poesia", "Llibre de text" };

    for (final String name : genres) {
      final Genre genre = new Genre();
      genre.setName(name);
      genreRepository.save(genre);
    }
  }

  private void seedThemesTable() {
    final String[] themes = { "Ciència ficció", "Històrica", "Psicològica", "Romàntica", "Policíaca", "Aprenentage",
        "Altres" };

    for (final String name : themes) {
      final Theme theme = new Theme();
      theme.setName(name);
      themeRepository.save(theme);
    }
  }

  private void seedLanguagesTable() {
    final String[] languages = { "Català", "Castellà", "Anglès" };

    for (final String name : languages) {
      final Language language = new Language();
      language.setName(name);
      languageRepository.save(language);
    }
  }

  private void seedPublishersTable() {
    final String[] publishers = { "Anagrama", "Planeta", "Edicions 62" };

    for (final String name : publishers) {
      final Publisher publisher = new Publisher();
      publisher.setName(name);
      publisherRepository.save(publisher);
    }
  }

  private void seedBookStatusesTable() {
    final String[] bookStatuses = { "available", "unavailable", "reserved" };

    for (final String name : bookStatuses) {
      final BookStatus bookStatus = new BookStatus();
      bookStatus.setName(name);
      bookStatusRepository.save(bookStatus);
    }
  }

  private void seedBooksTable() {
    final Long authorsCount = authorRepository.count();

    for (int id = 1; id <= authorsCount; id++) {
      final Book book = new Book();
      book.setTitle(faker.book().title());
      book.setAuthorId(random(1, 10));
      book.setGenreId(random(1, 3));
      book.setLanguageId(random(1, 3));
      book.setUserId(id % 2 == 1 ? 1 : 2);
      book.setPublisherId(random(1, 3));
      book.setEdition("1st edition");
      books.add(book);
      if (persist) {
        bookRepository.save(book);
      }
    }
  }

  private void seedExchangeStatusesTable() {
    final String[] exchangeStatuses = { "open", "pending", "closed" };

    for (final String name : exchangeStatuses) {
      final ExchangeStatus exchangeStatus = new ExchangeStatus();
      exchangeStatus.setName(name);
      exchangeStatusRepository.save(exchangeStatus);
    }
  }

  private void seedExchangesTable() {
    final Exchange exchange = new Exchange();
    exchange.setStatusId(random(1, 3));
    exchangeRepository.save(exchange);
  }

  private void seedMessagesTable() {
    for (int userId = 1; userId <= 2; userId++) {
      final Message message = new Message();
      message.setExchange(exchangeRepository.findById(1).get());
      message.setSender(userRepository.findById(userId).get());
      message.setBody(faker.backToTheFuture().quote());
      messageRepository.save(message);
    }
  }

  private int random(final int min, final int max) {
    return (int) (Math.random() * ((max - min) + 1)) + min;
  }
}