package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.model.dao.IReviewDAO;
import review.model.entity.Review;

import java.util.List;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private IReviewDAO reviewDAO;

    public void save(Review review) {
        reviewDAO.saveReview(review);
    }

    public List<Review> getAll() {
        return reviewDAO.getAll();
    }

    public void delete(Review review) {
        reviewDAO.deleteReview(review);
    }

    public Review getById(int id) {
        return reviewDAO.getById(id);
    }

    public List<Review> getByTitleId(int idTitle) {
        return reviewDAO.getByTitleId(idTitle);
    }

    public List<Review> getLastAddedReviewsByLimit(int limit) {
        return reviewDAO.getLastAddedReviewsByLimit(limit);
    }
}
