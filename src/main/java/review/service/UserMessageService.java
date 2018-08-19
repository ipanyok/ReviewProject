package review.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import review.model.dao.IUserMessageDAO;
import review.model.entity.User;
import review.model.entity.UserMessage;

import java.util.List;

@Service
@Transactional
public class UserMessageService {

    @Autowired
    private IUserMessageDAO userMessagesDAO;

    public void save(UserMessage userMessage) {
        userMessagesDAO.saveMessage(userMessage);
    }

    public List<UserMessage> getAll() {
        return userMessagesDAO.getAll();
    }

    public void delete(UserMessage userMessage) {
        userMessagesDAO.deleteMessage(userMessage);
    }

    public UserMessage getById(int id) {
        return userMessagesDAO.getById(id);
    }

    public List<UserMessage> getByAdminBufferId(int id) {
        return userMessagesDAO.getByAdminBufferId(id);
    }

    public int getCountNotReaded(User user) {
        return userMessagesDAO.getCountNotReaded(user);
    }
}
