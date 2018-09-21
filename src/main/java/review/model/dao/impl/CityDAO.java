package review.model.dao.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import review.model.dao.ICityDAO;
import review.model.entity.City;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CityDAO implements ICityDAO {

    private static final Logger logger = Logger.getLogger(CityDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveCity(City city) {
        if (city.getId() == null) {
            entityManager.persist(city);
            logger.info("City " + city.getName() + " was create");
        } else {
            entityManager.merge(city);
            logger.info("City[id=" + city.getId() + "] was update");
        }
    }

    @Override
    public List<City> getAll() {
        return entityManager.createNamedQuery("City.getAll", City.class).getResultList();
    }

    @Override
    public void deleteCity(City city) {
        City merge = entityManager.merge(city);
        entityManager.remove(merge);
    }

    @Override
    public City getById(int id) {
        try {
            TypedQuery<City> query = entityManager.createNamedQuery("City.getById", City.class).setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public City getByName(String name) {
        try {
            TypedQuery<City> query = entityManager.createNamedQuery("City.getByName", City.class).setParameter("name", name);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
