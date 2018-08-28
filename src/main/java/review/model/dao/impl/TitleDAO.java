package review.model.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import review.model.dao.ITitleDAO;
import review.model.entity.Title;
import review.servlet.beans.TitlesBean;

import javax.persistence.*;
import java.util.List;

@Repository
public class TitleDAO implements ITitleDAO {

    private Log logger = LogFactory.getLog(TitleDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveTitle(Title title) {
        if (title.getId() == null) {
            entityManager.persist(title);
            logger.info("Title " + title.getTitle() + " was create");
        } else {
            entityManager.merge(title);
            logger.info("Title[id=" + title.getId() + "] was update");
        }
    }

    @Override
    public List<Title> getAll() {
        return entityManager.createNamedQuery("Title.getAll", Title.class).getResultList();
    }

    @Override
    public void deleteTitle(Title title) {
        Title merge = entityManager.merge(title);
        entityManager.remove(merge);
    }

    @Override
    public Title getById(int id) {
        try {
            TypedQuery<Title> query = entityManager.createNamedQuery("Title.getById", Title.class).setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Title> getByName(String name) {
        TypedQuery<Title> query = entityManager.createNamedQuery("Title.getByName", Title.class).setParameter("title", name);
        return query.getResultList();
    }

    @Override
    public List<Title> getBySubCategoryId(int id) {
        TypedQuery<Title> query = entityManager.createNamedQuery("Title.getBySubCategoryId", Title.class).setParameter("idSubCategory", id);
        return query.getResultList();
    }

    @Override
    public List<TitlesBean> getBySubCategoryIdWithCity(int id) {
        Query query = entityManager.createQuery("select new review.servlet.beans.TitlesBean(t.id, t.title, t.description, c.name) from Title t, City c where t.idCity = c.id and t.idSubCategory = :idSubCategory order by t.id", TitlesBean.class).setParameter("idSubCategory", id);
        return query.getResultList();
    }

}
