package review.model.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import review.model.dao.IUserMessageDAO;
import review.model.entity.UserMessage;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserMessageDAO implements IUserMessageDAO {

    private Log logger = LogFactory.getLog(CategoryDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveMessage(UserMessage userMessage) {
        if (userMessage.getId() == null) {
            entityManager.persist(userMessage);
            logger.info("UserMessage was create");
        } else {
            entityManager.merge(userMessage);
            logger.info("UserMessage[id=" + userMessage.getId() + "] was update");
        }
    }

    @Override
    public List<UserMessage> getAll() {
        return entityManager.createNamedQuery("UserMessage.getAll", UserMessage.class).getResultList();
    }

    @Override
    public void deleteMessage(UserMessage userMessage) {
        UserMessage merge = entityManager.merge(userMessage);
        entityManager.remove(merge);
    }

    @Override
    public UserMessage getById(int id) {
        try {
            TypedQuery<UserMessage> query = entityManager.createNamedQuery("UserMessage.getById", UserMessage.class).setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<UserMessage> getByAdminBufferId(int id) {
        TypedQuery<UserMessage> query = entityManager.createNamedQuery("UserMessage.getByAdminBufferId", UserMessage.class).setParameter("idAdminBuffer", id);
        return query.getResultList();
    }
}
