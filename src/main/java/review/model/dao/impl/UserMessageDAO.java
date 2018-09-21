package review.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import review.model.dao.IUserMessageDAO;
import review.model.entity.User;
import review.model.entity.UserMessage;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserMessageDAO implements IUserMessageDAO {

    private static final Logger logger = Logger.getLogger(UserMessageDAO.class);

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String query = "select count(*) from reviews.usermessage u, reviews.adminbuffer a where u.idAdminBuffer = a.id and u.isread = false and a.userName = ?";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveMessage(UserMessage userMessage) {
        if (userMessage.getId() == null) {
            entityManager.persist(userMessage);
            logger.info("Message send to user");
        } else {
            entityManager.merge(userMessage);
            logger.info("Message[id=" + userMessage.getId() + "] was update");
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
    public UserMessage getByAdminBufferId(int id) {
        try {
            TypedQuery<UserMessage> query = entityManager.createNamedQuery("UserMessage.getByAdminBufferId", UserMessage.class).setParameter("idAdminBuffer", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public int getCountNotReaded(User user) {
        return jdbcTemplate.queryForObject(query, new Object[]{user.getLogin()}, Integer.class);
    }
}
