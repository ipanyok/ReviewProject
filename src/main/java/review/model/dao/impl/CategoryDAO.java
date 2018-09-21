package review.model.dao.impl;

import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger(CategoryDAO.class);

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
        List<Category> result = entityManager.createNamedQuery("Category.getAll", Category.class).getResultList();
        if (result.size() == 0) {
            return null;
        }
        return result;
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
