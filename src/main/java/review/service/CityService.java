package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.model.dao.ICityDAO;
import review.model.entity.City;

import java.util.List;

@Service
@Transactional
public class CityService {

    @Autowired
    private ICityDAO cityDAO;

    public void save(City city) {
        cityDAO.saveCity(city);
    }

    public List<City> getAll() {
        return cityDAO.getAll();
    }

    public void delete(City city) {
        cityDAO.deleteCity(city);
    }

    public City getById(int id) {
        return cityDAO.getById(id);
    }

    public City getByName(String name) {
        return cityDAO.getByName(name);
    }
}
