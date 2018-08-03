package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.model.dao.ICityDAO;
import review.model.dao.IUserDAO;
import review.model.entity.City;
import review.model.entity.User;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ICityDAO cityDAO;

    public void save(User user, String cityName) {
        City city = cityDAO.getByName(cityName);
        if (city == null) {
            city = new City(cityName);
            cityDAO.saveCity(city);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }

    public List<User> getAll() {
        return userDAO.getAll();
    }

    public void delete(User user) {
        userDAO.deleteUser(user);
    }

    public User getById(int id) {
        return userDAO.getById(id);
    }

    public User getByLogin(String login) {
        return userDAO.getByLogin(login);
    }

}
