package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.model.dao.ICityDAO;
import review.model.dao.ITitleDAO;
import review.model.entity.City;
import review.model.entity.Title;
import review.servlet.beans.TitlesBean;

import java.util.List;

@Service
@Transactional
public class TitleService {

    @Autowired
    private ITitleDAO titleDAO;

    @Autowired
    private ICityDAO cityDAO;

    public void save(Title title, String cityName) {
        City city = cityDAO.getByName(cityName);
        if (city == null) {
            city = new City(cityName);
            cityDAO.saveCity(city);
        }
        title.setIdCity(city.getId());
        titleDAO.saveTitle(title);
    }

    public List<Title> getAll() {
        return titleDAO.getAll();
    }

    public void delete(Title title) {
        titleDAO.deleteTitle(title);
    }

    public Title getById(int id) {
        return titleDAO.getById(id);
    }

    public List<Title> getByName(String name) {
        return titleDAO.getByName(name);
    }

    public List<Title> getBySubCategoryId(int id) {
        return titleDAO.getBySubCategoryId(id);
    }

    public List<TitlesBean> getBySubCategoryIdWithCity(int id) {
        return titleDAO.getBySubCategoryIdWithCity(id);
    }

    public List<Title> getAllByLimit(int limit) {
        return titleDAO.getAllByLimit(limit);
    }
}
