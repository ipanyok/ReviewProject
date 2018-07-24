package review.model.dao;

import review.model.entity.UserMessage;

import java.util.List;

public interface IUserMessageDAO {

    void saveMessage(UserMessage userMessage);

    List<UserMessage> getAll();

    void deleteMessage(UserMessage userMessage);

    UserMessage getById(int id);

    List<UserMessage> getByAdminBufferId(int id);

}
