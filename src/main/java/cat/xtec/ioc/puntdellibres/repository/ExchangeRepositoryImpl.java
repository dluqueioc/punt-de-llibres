package cat.xtec.ioc.puntdellibres.repository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
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
    public void create(Integer bookId, Principal user) {
        String username = user.getName();
        User me = userRepository.findByUsername(username);
        Integer myUserId = me.getId();

        Book book = (Book) em.createQuery("SELECT book from Book book where id = ?1").setParameter(1, bookId)
                .getSingleResult();

        List<UserInExchange> myExchanges = me.getExchanges();
        List<Integer> myOpenExchanges = new ArrayList<>();
        for (UserInExchange exch : myExchanges) {
            if (exch.getExchange().getStatusId() == 1 || exch.getExchange().getStatusId() == 2) {
                myOpenExchanges.add(exch.getExchangeId());
            }
        }

        Exchange exchange = null;
        Boolean createExchange = false;

        if (myOpenExchanges.size() != 0) {
            List<UserInExchange> exchangesWithBothUsers = em.createQuery(
                    "SELECT userInExchange FROM UserInExchange userInExchange WHERE userId = ?1 AND exchangeId IN :ids", UserInExchange.class)
                    .setParameter(1, book.getUserId()).setParameter("ids", myOpenExchanges).getResultList();

            if (exchangesWithBothUsers.size() != 0) {
                exchange = exchangesWithBothUsers.get(0).getExchange();
            } else {
                createExchange = true;
            }
        } else {
            createExchange = true;
        }

        if (createExchange) {
            exchange = new Exchange();
            exchange.setStarterUserId(myUserId);
            em.persist(exchange);

            UserInExchange user1 = new UserInExchange();
            user1.setExchangeId(exchange.getId());
            user1.setUserId(myUserId);
            em.persist(user1);

            UserInExchange user2 = new UserInExchange();
            user2.setExchangeId(exchange.getId());
            user2.setUserId(book.getUserId());
            em.persist(user2);
        } else {
            List<UserWantsBook> everyBookWantedInExchange = exchange.getBooks();
            Boolean otherUserHasMadeAProposal = false;

            for (UserWantsBook proposal : everyBookWantedInExchange) {
                if (proposal.getUserId() != myUserId) {
                    otherUserHasMadeAProposal = true;
                    break;
                }
            }

            if (otherUserHasMadeAProposal) {
                exchange.setStatusId(2);
                em.persist(exchange);
            }
        }

        UserWantsBook userWantsBook = new UserWantsBook();
        userWantsBook.setBookId(bookId);
        userWantsBook.setUserId(myUserId);
        userWantsBook.setExchangeId(exchange.getId());
        em.persist(userWantsBook);
    }

    @Override
    public Iterable<Exchange> findMyExchanges(Principal user) {
        String username = user.getName();
        User me = userRepository.findByUsername(username);

        Iterable<UserInExchange> meInExchanges = me.getExchanges();
        List<Exchange> myExchanges = new ArrayList<>();

        for (UserInExchange exc : meInExchanges) {
            String status = exc.getExchange().getStatus().getName();
            if (! (status.equals("rebutjat") || status.equals("eliminat"))) {
                myExchanges.add(exc.getExchange());
            }
        }

        return myExchanges;
    }
}