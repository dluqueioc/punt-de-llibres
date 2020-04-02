package cat.xtec.ioc.puntdellibres.repository;

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

@Repository
@Transactional
public class ExchangeRepositoryImpl implements ExchangeRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Exchange create(Integer bookId) {
        Book book = (Book) em
            .createQuery("SELECT book from Book book where id = ?1")
            .setParameter(1, bookId)
                .getSingleResult();

        Exchange exchange = null;

        try {
            exchange = (Exchange) em
            .createQuery("SELECT exchange from Exchange exchange where status_id = 1 AND id IN (SELECT userInExchange.exchangeId FROM UserInExchange userInExchange WHERE user_id = ?1)")
            .setParameter(1, book.getUserId())
                .getSingleResult();
        } catch (NoResultException ex) {
        }

        if (exchange == null) {
            exchange = new Exchange();
        }

        return exchange;
    }
}