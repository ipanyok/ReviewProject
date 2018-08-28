package review.model.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import review.model.dao.IReviewDAO;
import review.model.entity.Review;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class ReviewDAO implements IReviewDAO {

    private Log logger = LogFactory.getLog(ReviewDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveReview(Review review) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        review.setDate(timestamp);
        if (review.getId() == null) {
            entityManager.persist(review);
            logger.info("Review " + review.getReviewName() + " was create");
        } else {
            entityManager.merge(review);
            logger.info("Review[id=" + review.getId() + "] was update");
        }
    }

    @Override
    public List<Review> getAll() {
        return entityManager.createNamedQuery("Review.getAll", Review.class).getResultList();
    }

    @Override
    public void deleteReview(Review review) {
        Review merge = entityManager.merge(review);
        entityManager.remove(merge);
    }

    @Override
    public Review getById(int id) {
        try {
            TypedQuery<Review> query = entityManager.createNamedQuery("Review.getById", Review.class).setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Review> getByTitleId(int idTitle) {
        TypedQuery<Review> query = entityManager.createNamedQuery("Review.getByTitleId", Review.class).setParameter("idTitle", idTitle);
        return query.getResultList();

    }

    @Override
    public List<Review> getLastAddedReviewsByLimit(int limit) {
        TypedQuery<Review> query = entityManager.createNamedQuery("Review.getLastAdded", Review.class).setMaxResults(limit);
        return query.getResultList();
    }
}
