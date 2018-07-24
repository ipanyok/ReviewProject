package review.model.dao;

import review.model.entity.Review;

import java.util.List;

public interface IReviewDAO {

    void saveReview(Review review);

    List<Review> getAll();

    void deleteReview(Review review);

    Review getById(int id);

    List<Review> getByTitleId(int idTitle);

    List<Review> getLastAddedReviewsByLimit(int limit);
}
