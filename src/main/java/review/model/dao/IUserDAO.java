package review.model.dao;

import review.model.entity.User;

import java.util.List;

public interface IUserDAO {

    void saveUser(User user);

    List<User> getAll();

    void deleteUser(User user);

    User getById(int id);

    User getByLogin(String login);
}
