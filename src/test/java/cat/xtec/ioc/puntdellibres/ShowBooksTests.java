package cat.xtec.ioc.puntdellibres;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import cat.xtec.ioc.puntdellibres.db.seeders.MainSeeder;
import cat.xtec.ioc.puntdellibres.repository.BookRepository;
import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.User;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ShowBooksTests {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  MainSeeder seeder;

  @MockBean
  private BookRepository bookRepository;

  private final String title = "El llibre de les il·lusions";

  @BeforeEach
  public void setup() throws Exception {
    User user = new User();
    user.setUsername("mike");

    Book book = new Book();
    book.setTitle(title);
    book.setUser(user);
    final List<Book> books = new ArrayList<Book>();
    books.add(book);

    when(bookRepository.findAll()).thenReturn(books);
  }

  @Test
  public void anyoneCanSeeAvailableBooks() throws Exception {
    this.mockMvc.perform(get("/llibres-disponibles")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString(title)));
  }

  @Test
  public void anonymousUsersCantSeeBookOwners() throws Exception {
    this.mockMvc.perform(get("/llibres-disponibles")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString(title))).andExpect(content().string(not(containsString("mike"))));
  }

  @Test
  @WithMockUser(username = "user1", password = "pwd", roles = "USER")
  public void registeredUsersCanSeeBookOwners() throws Exception {
    this.mockMvc.perform(get("/llibres-disponibles")).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString(title))).andExpect(content().string((containsString("mike"))));
  }
}