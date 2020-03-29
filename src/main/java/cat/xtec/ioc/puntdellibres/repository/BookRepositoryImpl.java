package cat.xtec.ioc.puntdellibres.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cat.xtec.ioc.puntdellibres.model.Book;

@Repository
@Transactional(readOnly = true)
public class BookRepositoryImpl implements BookRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    @Override
    public Iterable<Book> findAllButMine(Integer userId) {
        // Query q = this.em.createQuery("SELECT o FROM Order o JOIN FETCH o.items i
        // WHERE o.id = :id");

        TypedQuery<Book> query = em.createQuery(
                "SELECT book FROM Book book WHERE book.userId != :userId AND book.bookStatusId = 1", Book.class);
        Iterable<Book> books = query.setParameter("userId", userId).getResultList();
        return books;
    }
}