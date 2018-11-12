package review.model.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import review.model.dao.IRatingDAO;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Repository
public class RatingDAO implements IRatingDAO {

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String query = "select sum(MARK)/count(*) from reviews.REVIEW where IDTITLE = ?";

    @Override
    public Double getMiddleMark(int id) {
        return jdbcTemplate.queryForObject(query, new Object[]{id}, Double.class);
    }
}
