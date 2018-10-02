package review.servlet.utils.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import review.model.entity.Review;

@Component
public class ReviewValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Review.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Review review = (Review) o;
        if (review.getMark() < 1 || review.getMark() > 5) {
            errors.rejectValue("mark", "NotEmpty.mark");
        }
    }
}
