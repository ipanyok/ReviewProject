package review.model.dao;

import review.model.entity.UserMessage;

import java.util.List;

public interface IUserMessagesDAO {

    void addMessage(UserMessage userMessage);

    List<UserMessage> getAll();

    void deleteMessage(UserMessage userMessage);

    void updateMessage(UserMessage userMessage);

    UserMessage getById(int id);

    List<UserMessage> getByAdminBufferId(int id);

}
