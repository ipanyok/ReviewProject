package review.model.dao;

import review.model.entity.City;

import java.util.List;

public interface ICityDAO {

    void saveCity(City city);

    List<City> getAll();

    void deleteCity(City city);

    City getById(int id);

    City getByName(String name);
}
