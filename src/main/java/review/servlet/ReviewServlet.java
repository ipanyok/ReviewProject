package review.servlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import review.model.entity.City;
import review.model.entity.Review;
import review.model.entity.Title;
import review.model.entity.User;
import review.service.CityService;
import review.service.ReviewService;
import review.service.TitleService;
import review.service.UserService;
import review.servlet.beans.ReviewsBean;
import review.servlet.utils.validator.ReviewValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReviewServlet {

    private static final Logger logger = Logger.getLogger(ReviewServlet.class);

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewValidator reviewValidator;

    @GetMapping("/titles/{id}")
    public String getReviews(@PathVariable("id") int idTitle, Model model) {
        logger.info("Start showReviews()...");
        List<Review> reviews = reviewService.getByTitleId(idTitle);
        Title title = titleService.getById(idTitle);
        City city = cityService.getById(title.getIdCity());
        List<ReviewsBean> reviewsBeans = new ArrayList<>();
        if (!reviews.isEmpty()) {
            for (Review review : reviews) {
                User user = userService.getById(review.getIdUser());
                reviewsBeans.add(new ReviewsBean(review.getReviewName(), review.getMark(), review.getText(), review.getDate(), user.getLogin()));
            }
        }
        model.addAttribute("city", city);
        model.addAttribute("title", title);
        model.addAttribute("reviews", reviewsBeans);
        return "reviews";
    }

    @GetMapping("/titles/{idTitle}/addreview")
    public String getAddReviewForm(@PathVariable("idTitle") int idTitle, Model model, Principal principal) {
        model.addAttribute("idTitle", idTitle);
        model.addAttribute("review", new Review());
        User currentUser = userService.getByLogin(principal.getName());
        List<Review> reviewsByCurrentTitle = reviewService.getByTitleId(idTitle);
        boolean isReviewExist = false;
        for (Review elem : reviewsByCurrentTitle) {
            if (userService.getById(elem.getIdUser()).getLogin().equals(currentUser.getLogin())) {
                isReviewExist = true;
                break;
            }
        }
        if (isReviewExist) {
            model.addAttribute("message", "YOU ALREADY COMMENT THIS TITLE");
            return getReviews(idTitle, model);
        }
        return "addreview";
    }

    @PostMapping("/titles/{idTitle}/addreview")
    public String addReview(@ModelAttribute @Valid Review review, BindingResult bindingResult, Principal principal, Model model) {
        reviewValidator.validate(review, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("idTitle", review.getIdTitle());
            logger.error("Review validate error");
            return "addreview";
        }
        User currentUser = userService.getByLogin(principal.getName());
        review.setIdUser(currentUser.getId());
        reviewService.save(review);
        logger.info("Review was added");
        return "redirect:/titles/" + review.getIdTitle();
    }


}
