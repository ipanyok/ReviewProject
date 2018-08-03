package review.servlet.utils.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import review.model.dao.IUserDAO;
import review.model.entity.User;

@Component
public class UserValidator implements Validator {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (userDAO.getByLogin(user.getLogin()) != null) {
            errors.rejectValue("login", "error.login.unique");
        }
    }
}
