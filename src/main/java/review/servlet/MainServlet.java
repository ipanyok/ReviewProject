package review.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import review.model.dao.ICityDAO;
import review.model.entity.*;
import review.service.*;
import review.servlet.beans.PagesBean;
import review.servlet.beans.ReviewsBean;
import review.servlet.beans.TitlesBean;
import review.servlet.utils.Pagination;
import review.servlet.utils.RatingUtils;
import review.servlet.utils.validator.UserValidator;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class MainServlet {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private AdminBufferService adminBufferService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Value("6")
    private int paginationTotal;

    @Value("3")
    private int limitReviews;

    @Value("3")
    private int countRatings;

    @Value("3")
    private int ratingMiddleMark;

    private final String ALL_TITLES_FROM_CATEGORY = "titles";

    @GetMapping({"/", "/home"})
    public String showMain(HttpSession session, Model model, Principal principal) {
        if (session.getAttribute("categoryMap") == null) {
            Map<String, List<SubCategory>> categoryMap = new TreeMap<>();
            List<Category> categories = categoryService.getAll();
            for (Category elem : categories) {
                List<SubCategory> subCategories = subCategoryService.getByCategoryId(elem.getId());
                categoryMap.put(elem.getName(), subCategories);
            }
            session.setAttribute("categoryMap", categoryMap);
        }

        List<Review> lastAddedReviews = reviewService.getLastAddedReviewsByLimit(limitReviews);
        Map<Review, String> mapReviewWithTitles = new HashMap<>();
        for (Review elem : lastAddedReviews) {
            City city = cityService.getById(titleService.getById(elem.getIdTitle()).getIdCity());
            mapReviewWithTitles.put(elem, titleService.getById(elem.getIdTitle()).getTitle() + " (" + city.getName() + ")");
        }
        model.addAttribute("lastAddedReviews", mapReviewWithTitles);

        List<City> cities = cityService.getAll();
        List<String> cityNames = new ArrayList<>();
        for (City city : cities) {
            cityNames.add(city.getName());
        }
        session.setAttribute("cities", cityNames);

        if (principal != null) {
            if (!principal.getName().equals("Admin")) {
                User currentUser = userService.getByLogin(principal.getName());
                session.setAttribute("currentCity", currentUser.getCity());
                Iterator<String> iterator = cityNames.iterator();
                while (iterator.hasNext()) {
                    String elem = iterator.next();
                    if (elem.equals(currentUser.getCity())) {
                        iterator.remove();
                        break;
                    }
                }
                session.setAttribute("cities", cityNames);
                session.setAttribute("messagesmenu", userMessageService.getCountNotReaded(currentUser));
                return "home";
            } else {
                session.setAttribute("currentCity", "Get your choice");
                session.setAttribute("messagesmenu", adminBufferService.getCountFromUsers());
                return "admin";
            }
        }
        return "home";
    }

    @GetMapping("/titles/subcat/{id}")
    public String getTitle(@PathVariable("id") int idSubCategory, HttpSession session, Model model) {
        List<TitlesBean> titles = titleService.getBySubCategoryIdWithCity(idSubCategory);
        session.setAttribute(ALL_TITLES_FROM_CATEGORY, titles);

        // pagination
        List<PagesBean> pagesList = Pagination.pagesCount((List<Title>) session.getAttribute(ALL_TITLES_FROM_CATEGORY), paginationTotal);
        session.setAttribute("countPages", pagesList);
        List<Title> titlesPagination = Pagination.printResult((List<Title>) session.getAttribute(ALL_TITLES_FROM_CATEGORY), 0, paginationTotal);
        model.addAttribute(ALL_TITLES_FROM_CATEGORY, titlesPagination);
        //
        return "titles";
    }

    @GetMapping("/title/page/{number}")
    public String getPage(@PathVariable("number") int page, HttpSession session, Model model) {
        List<Title> titlesPagination = Pagination.printResult((List<Title>) session.getAttribute(ALL_TITLES_FROM_CATEGORY), page * paginationTotal - paginationTotal, paginationTotal);
        model.addAttribute(ALL_TITLES_FROM_CATEGORY, titlesPagination);
        return "titles";
    }

    @GetMapping("/titles/{id}")
    public String getTitle(@PathVariable("id") int idTitle, Model model) {
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

    @GetMapping("/ratings")
    public String getRating(Model model) {
        List<Title> titles = titleService.getAll();
        Map<String, Double> ratingMap = new HashMap<>();
        for (Title title : titles) {
            Double middleMark = ratingService.getMiddleMark(title.getId());
            if (middleMark != null) {
                ratingMap.put(title.getTitle() + " (" + cityService.getById(title.getIdCity()).getName() + ")", middleMark);
            }
        }
        model.addAttribute("ratings", RatingUtils.createRating(ratingMap, countRatings, ratingMiddleMark));
        return "ratings";
    }

    @GetMapping("/login")
    public String getLogin(Model model, String error) {
        if (error != null) {
            model.addAttribute("error", "error.login");
        }
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid User user, BindingResult bindingResult, @RequestParam("city") String city, @RequestParam("confirmPassword") String confirmPassword) {
        userValidator.validate(user, bindingResult);
        if (!user.getPassword().equals(confirmPassword)) {
            bindingResult.rejectValue("password", "error.password.confirm");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.save(user, city);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getLogin(), confirmPassword);
        authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/home";
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
            return getTitle(idTitle, model);
        }
        return "addreview";
    }

    @PostMapping("/titles/{idTitle}/addreview")
    public String addReview(@ModelAttribute @Valid Review review, BindingResult bindingResult, Principal principal, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("idTitle", review.getIdTitle());
            return "addreview";
        }
        User currentUser = userService.getByLogin(principal.getName());
        review.setIdUser(currentUser.getId());
        reviewService.save(review);
        return "redirect:/titles/" + review.getIdTitle();
    }

    @GetMapping("/titles/subcat/{idSubCat}/addreviewtonewtitle")
    public String getAddReviewToNewTitle(@PathVariable("idSubCat") int idSubCat, Model model) {
        model.addAttribute("idSubCat", idSubCat);
        return "addreviewtonewtitle";
    }

    @PostMapping("/titles/subcat/{idSubCat}")
    public String addAdminBuffer(@PathVariable("idSubCat") int idSubCategory, Principal principal, RedirectAttributes redirectAttributes,
                                 @RequestParam("titleName") String titleName,
                                 @RequestParam("titleDescription") String titleDescription,
                                 @RequestParam("titleCity") String titleCity,
                                 @RequestParam("text") String reviewText,
                                 @RequestParam("reviewName") String reviewName,
                                 @RequestParam("mark") int mark) {
        AdminBuffer adminBuffer = new AdminBuffer();
        adminBuffer.setUserName(userService.getByLogin(principal.getName()).getLogin());
        adminBuffer.setCategoryName(categoryService.getById(subCategoryService.getById(idSubCategory).getIdCategory()).getName());
        adminBuffer.setSubCategoryName(subCategoryService.getById(idSubCategory).getName());
        adminBuffer.setTitleName(titleName);
        adminBuffer.setTitleDescription(titleDescription);
        adminBuffer.setTitleCity(titleCity);
        adminBuffer.setReviewText(reviewText);
        adminBuffer.setReviewName(reviewName);
        adminBuffer.setMark(mark);
        adminBuffer.setAdd(true);
        adminBufferService.save(adminBuffer);
        redirectAttributes.addFlashAttribute("userMessage", "You comment send to Admin");
        return "redirect:/titles/subcat/" + idSubCategory;
    }

    @GetMapping("/showmessages")
    public String getMessages(Model model, Principal principal) {
        if (principal.getName().equals("Admin")) {
            List<AdminBuffer> adminBufferList = adminBufferService.getAll();
            model.addAttribute("adminBufferList", adminBufferList);
            return "showadminmessages";
        }
        return "";
    }

}
