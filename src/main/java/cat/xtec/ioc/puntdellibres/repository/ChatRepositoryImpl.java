package cat.xtec.ioc.puntdellibres.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cat.xtec.ioc.puntdellibres.model.Chat;

@Repository
@Transactional(readOnly = true)
public class ChatRepositoryImpl implements ChatRepositoryCustom {
    @PersistenceContext
    EntityManager em;

    @Override
    public Iterable<Chat> getByUserId(Integer userId) {
        TypedQuery<Chat> query = em.createQuery(
                "SELECT chat FROM Chat chat WHERE chat.user1Id = :userId OR chat.user2Id = :userId", Chat.class);
        Iterable<Chat> chats = query.setParameter("userId", userId).getResultList();
        return chats;
    }

    @Override
    public Iterable<Chat> getByUserIds(Integer user1Id, Integer user2Id) throws NoResultException {
        TypedQuery<Chat> query = em.createQuery(
                "SELECT chat FROM Chat chat WHERE (chat.user1Id = :user1Id AND chat.user2Id = :user2Id) OR (chat.user1Id = :user2Id AND chat.user2Id = :user1Id)",
                Chat.class);
        return query.setParameter("user1Id", user1Id).setParameter("user2Id", user2Id).getResultList();
    }
}