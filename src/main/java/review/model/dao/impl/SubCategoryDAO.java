package review.model.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import review.model.dao.ISubCategoryDAO;
import review.model.entity.SubCategory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class SubCategoryDAO implements ISubCategoryDAO {

    private Log logger = LogFactory.getLog(CategoryDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveSubCategory(SubCategory subCategory) {
        if (subCategory.getId() == null) {
            entityManager.persist(subCategory);
            logger.info("Subcategory " + subCategory.getName() + " was create");
        } else {
            entityManager.merge(subCategory);
            logger.info("Subcategory[id=" + subCategory.getId() + "] was update");
        }
    }

    @Override
    public List<SubCategory> getAll() {
        return entityManager.createNamedQuery("SubCategory.getAll", SubCategory.class).getResultList();
    }

    @Override
    public void deleteSubCategory(SubCategory subCategory) {
        SubCategory merge = entityManager.merge(subCategory);
        entityManager.remove(merge);
    }

    @Override
    public SubCategory getById(int id) {
        try {
            TypedQuery<SubCategory> query = entityManager.createNamedQuery("SubCategory.getById", SubCategory.class).setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<SubCategory> getByCategoryId(int idCategory) {
        TypedQuery<SubCategory> query = entityManager.createNamedQuery("SubCategory.getByCategoryId", SubCategory.class).setParameter("idCategory", idCategory);
        return query.getResultList();
    }
}
