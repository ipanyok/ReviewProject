package review.model.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import review.model.dao.ICategoryDAO;
import review.model.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryDAO implements ICategoryDAO {

    private Log logger = LogFactory.getLog(CategoryDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveCategory(Category category) {
        if (category.getId() == null) {
            entityManager.persist(category);
            logger.info("Category " + category.getName() + " was create");
        } else {
            entityManager.merge(category);
            logger.info("Category[id=" + category.getId() + "] was update");
        }
    }

    @Override
    public List<Category> getAll() {
        return entityManager.createNamedQuery("Category.getAll", Category.class).getResultList();
    }

    @Override
    public void deleteCategory(Category category) {
        Category merge = entityManager.merge(category);
        entityManager.remove(merge);
    }

    @Override
    public Category getById(int id) {
        try {
            TypedQuery<Category> query = entityManager.createNamedQuery("Category.getById", Category.class).setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Category getByName(String name) {
        try {
            TypedQuery<Category> query = entityManager.createNamedQuery("Category.getByName", Category.class).setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
