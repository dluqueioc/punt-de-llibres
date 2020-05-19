package cat.xtec.ioc.puntdellibres.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vividsolutions.jts.geom.Geometry;

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
        TypedQuery<Book> query = em.createQuery(
                "SELECT book FROM Book book WHERE book.userId != :userId AND book.bookStatusId = 1", Book.class);
        Iterable<Book> books = query.setParameter("userId", userId).getResultList();
        return books;
    }

	@Override
	public Iterable<Book> findByDistance(int userId, int radius) {
		//hem de transformar la distància a les unitats de SRID
		Double distanceDegrees = (double) (radius*0.009);
		TypedQuery<Geometry> q = em.createQuery("SELECT user.geoLocation FROM User user WHERE user.id = :userId", Geometry.class);
		//recuperem la posició de l'usuari per passar-lo a la consulta
		Geometry p = q.setParameter("userId", userId).getSingleResult();
		TypedQuery<Book> query = em.createQuery(
				"SELECT book FROM Book book LEFT JOIN User user ON book.userId = user.id WHERE book.userId != :userId AND book.bookStatusId = 1 AND dwithin(user.geoLocation, :centerPoint, :distanceDegrees) = true ORDER BY book.createdDate DESC", Book.class);
		query.setParameter("userId", userId);
		query.setParameter("centerPoint", p);
		query.setParameter("distanceDegrees", distanceDegrees);
		Iterable<Book> books = query.getResultList();
		return books;
	}
}