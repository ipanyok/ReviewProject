package review.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import review.model.dao.ICityDAO;
import review.model.entity.*;
import review.service.*;
import review.servlet.beans.PagesBean;
import review.servlet.beans.TitlesBean;
import review.servlet.utils.Pagination;
import review.servlet.utils.RatingUtils;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
    private ICityDAO cityDAO;

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
    public String showMain(HttpSession session, Model model) {
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
            mapReviewWithTitles.put(elem, titleService.getById(elem.getIdTitle()).getTitle());
        }
        model.addAttribute("lastAddedReviews", mapReviewWithTitles);
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
        model.addAttribute("reviews", reviews);
        return "reviews";
    }

    @GetMapping("/ratings")
    public String getRating(Model model) {
        List<Title> titles = titleService.getAll();
        Map<String, Double> ratingMap = new HashMap<>();
        for (Title title : titles) {
            Double middleMark = ratingService.getMiddleMark(title.getId());
            if (middleMark != null) {
                ratingMap.put(title.getTitle() + "(" + cityDAO.getById(title.getIdCity()).getName() + ")", middleMark);
            }
        }
        model.addAttribute("ratings", RatingUtils.createRating(ratingMap, countRatings, ratingMiddleMark));
        return "ratings";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid User user, BindingResult bindingResult, @RequestParam("city") String city) {
        System.out.println(user);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.save(user, city);
        return "redirect:/home";
    }





    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userService.getById(id);
        userService.delete(user);
        return "redirect:/users";
    }

    @GetMapping("/user")
    public String getUserInfo(@RequestParam("login") String login, Model model) {
        model.addAttribute("user", userService.getByLogin(login));
        return "showuser";
    }

    @GetMapping("/user/{id}")
    public String getUserInfoId(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "showuser";
    }

    @GetMapping("/update/{id}")
    public String showUpdateUserForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "updateuser";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute User user, @RequestParam("city") String city) {
        userService.save(user, city);
        return "redirect:/users";
    }

//    @PostMapping("/adduser")
//    public String addUser(@RequestParam("firstName") String firstName,
//                          @RequestParam("lastName") String lastName,
//                          @RequestParam("login") String login,
//                          @RequestParam("email") String email,
//                          @RequestParam("password") String password,
//                          @RequestParam("firstName") String city) {
//
//        userService.addUser(new User(firstName, lastName, login, email, password, city));
//        return "redirect:users";
//    }

}
