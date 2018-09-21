package review.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import review.model.dao.IAdminBufferDAO;
import review.model.entity.AdminBuffer;

import javax.annotation.Resource;
import javax.persistence.*;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class AdminBufferDAO implements IAdminBufferDAO {

    private static final Logger logger = Logger.getLogger(AdminBufferDAO.class);

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    private final String query = "select count(*) from reviews.adminbuffer where isadd = true";
    private final String queryForStatusRequests = "select isAdd, cancel from reviews.adminbuffer where id = ?";

    @Override
    public void saveBuffer(AdminBuffer adminBuffer) {
        if (adminBuffer.getId() == null) {
            entityManager.persist(adminBuffer);
            logger.info("Message send to admin");
        } else {
            entityManager.merge(adminBuffer);
            logger.info("Message update");
        }
    }

    @Override
    public List<AdminBuffer> getAll() {
        return entityManager.createNamedQuery("AdminBuffer.findAll", AdminBuffer.class).getResultList();
    }

    @Override
    public void deleteBuffer(AdminBuffer adminBuffer) {
        AdminBuffer merge = entityManager.merge(adminBuffer);
        entityManager.remove(merge);
    }

    @Override
    public AdminBuffer getById(int id) {
        try {
            TypedQuery<AdminBuffer> resultList = entityManager.createNamedQuery("AdminBuffer.findById", AdminBuffer.class).setParameter("id", id);
            return resultList.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<AdminBuffer> getByUserLogin(String userLogin) {
        TypedQuery<AdminBuffer> resultList = entityManager.createNamedQuery("AdminBuffer.findByUserLogin", AdminBuffer.class).setParameter("userName", userLogin);
        return resultList.getResultList();
    }

    @Override
    public int getCountFromUsers() {
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    @Override
    public Map<String, Object> getStatusRequests(Integer idAdminBuffer) {
        return jdbcTemplate.queryForMap(queryForStatusRequests, new Object[]{idAdminBuffer});
    }
}
