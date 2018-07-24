package review.model.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import review.model.dao.IUserDAO;
import review.model.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class UserDAO implements IUserDAO {

    private Log logger = LogFactory.getLog(CategoryDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveUser(User user) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        user.setCreateDate(timestamp);
        if (user.getId() == null) {
            entityManager.persist(user);
            logger.info("User " + user.getLogin() + " was create");
        } else {
            entityManager.merge(user);
            logger.info("User[id=" + user.getId() + "] was update");
        }
    }

    @Override
    public List<User> getAll() {
        return entityManager.createNamedQuery("User.getAll", User.class).getResultList();
    }

    @Override
    public void deleteUser(User user) {
        User merge = entityManager.merge(user);
        entityManager.remove(merge);
    }

    @Override
    public User getById(int id) {
        try {
            TypedQuery<User> query = entityManager.createNamedQuery("User.getById", User.class).setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getByLogin(String login) {
        try {
            TypedQuery<User> query = entityManager.createNamedQuery("User.getByLogin", User.class).setParameter("login", login);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
