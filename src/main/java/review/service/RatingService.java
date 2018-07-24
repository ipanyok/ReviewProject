package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.model.dao.IRatingDAO;

@Service
@Transactional
public class RatingService {

    @Autowired
    private IRatingDAO ratingDAO;

    public Double getMiddleMark(int id) {
        return ratingDAO.getMiddleMark(id);
    }
}
