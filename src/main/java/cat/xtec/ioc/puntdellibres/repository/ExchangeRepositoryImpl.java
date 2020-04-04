package cat.xtec.ioc.puntdellibres.repository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cat.xtec.ioc.puntdellibres.model.Book;
import cat.xtec.ioc.puntdellibres.model.Exchange;
import cat.xtec.ioc.puntdellibres.model.User;
import cat.xtec.ioc.puntdellibres.model.UserInExchange;
import cat.xtec.ioc.puntdellibres.model.UserWantsBook;

@Repository
@Transactional
public class ExchangeRepositoryImpl implements ExchangeRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Exchange create(Integer bookId, Principal user) {
        String username = user.getName();
        User me = userRepository.findByUsername(username);
        Integer myUserId = me.getId();

        Book book = (Book) em.createQuery("SELECT book from Book book where id = ?1").setParameter(1, bookId)
                .getSingleResult();

        Exchange exchange = new Exchange();
        em.persist(exchange);

        UserInExchange user1 = new UserInExchange();
        user1.setExchangeId(exchange.getId());
        user1.setUserId(myUserId);
        em.persist(user1);

        UserWantsBook userWantsBook = new UserWantsBook();
        userWantsBook.setBookId(bookId);
        userWantsBook.setUserId(myUserId);
        userWantsBook.setExchangeId(exchange.getId());
        em.persist(userWantsBook);

        UserInExchange user2 = new UserInExchange();
        user2.setExchangeId(exchange.getId());
        user2.setUserId(book.getUserId());
        em.persist(user2);

        return exchange;
    }

    @Override
    public Iterable<Exchange> findMyExchanges(Principal user) {
        String username = user.getName();
        User me = userRepository.findByUsername(username);
        Integer myUserId = me.getId();

        Iterable<UserInExchange> meInExchanges = me.getExchanges();
        List<Integer> myExchangeIds = new ArrayList<>();
        List<Exchange> myExchanges = new ArrayList<>();

        for (UserInExchange exc : meInExchanges) {
            myExchanges.add(exc.getExchange());
        }

        System.out.println("Size: " + myExchanges.size());

        return myExchanges;
    }
}