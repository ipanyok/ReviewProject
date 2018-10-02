package review.servlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import review.model.entity.*;
import review.service.*;
import review.servlet.beans.AdminBufferBean;
import review.servlet.beans.PagesBean;
import review.servlet.utils.Pagination;
import review.servlet.utils.RatingUtils;
import review.servlet.utils.validator.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
public class MainServlet {

    private static final Logger logger = Logger.getLogger(MainServlet.class);

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

    @Value("${pagination.size}")
    private int paginationTotal;

    @Value("${reviews.limit}")
    private int limitReviews;

    @Value("${rating.count}")
    private int countRatings;

    @Value("${rating.middlemark}")
    private int ratingMiddleMark;

    @Value("${admin.login}")
    private String adminLogin;

    @Value("${categories.attribute}")
    private String categoryMapAttribute;

    @GetMapping({"/", "/home"})
    public String showMain(HttpSession session, Model model, Principal principal) {
        if (session.getAttribute(categoryMapAttribute) == null) {
            List<Category> categories = categoryService.getAll();
            if (categories != null) {
                Map<String, List<SubCategory>> categoryMap = new TreeMap<>();
                for (Category elem : categories) {
                    List<SubCategory> subCategories = subCategoryService.getByCategoryId(elem.getId());
                    categoryMap.put(elem.getName(), subCategories);
                }
                session.setAttribute(categoryMapAttribute, categoryMap);
            } else {
                session.setAttribute(categoryMapAttribute, null);
            }
        }

        List<Review> lastAddedReviews = reviewService.getLastAddedReviewsByLimit(limitReviews);
        Map<Review, List<String>> mapReviewWithTitles = new HashMap<>();
        for (Review elem : lastAddedReviews) {
            City city = cityService.getById(titleService.getById(elem.getIdTitle()).getIdCity());
            List<String> lastAddedList = new ArrayList<>();
            lastAddedList.add(titleService.getById(elem.getIdTitle()).getTitle());
            lastAddedList.add(city.getName());
            mapReviewWithTitles.put(elem, lastAddedList);
        }
        model.addAttribute("lastAddedReviews", mapReviewWithTitles);

        List<City> cities = cityService.getAll();
        List<String> cityNames = new ArrayList<>();
        for (City city : cities) {
            cityNames.add(city.getName());
        }
        cityNames.add("All");
        session.setAttribute("cities", cityNames);

        if (principal != null) {
            if (!principal.getName().equals(adminLogin)) {
                User currentUser = userService.getByLogin(principal.getName());
                if (session.getAttribute("currentCity") == null) {
                    session.setAttribute("currentCity", currentUser.getCity());
                }
                session.setAttribute("messagesmenu", userMessageService.getCountNotReaded(currentUser));

                // review by city of current user
                if (!session.getAttribute("currentCity").equals("All")) {
                    Map<Review, List<String>> buf = new HashMap<>();
                    for (Map.Entry<Review, List<String>> entry : mapReviewWithTitles.entrySet()) {
                        if (entry.getValue().get(1).equals(session.getAttribute("currentCity"))) {
                            buf.put(entry.getKey(), entry.getValue());
                        }
                    }
                    model.addAttribute("lastAddedReviews", buf);
                }
                //

                return "home";
            } else {
                if (session.getAttribute("currentCity") == null) {
                    session.setAttribute("currentCity", "All");
                }
                session.setAttribute("messagesmenu", adminBufferService.getCountFromUsers());
                return "admin";
            }
        }
        return "home";
    }

    @GetMapping("/search")
    public String getSearch(HttpSession session, Model model) {
        // pagination
        List<PagesBean> pagesList = Pagination.pagesCount((List<Title>) session.getAttribute("searchResult"), paginationTotal);
        session.setAttribute("countPagesSearch", pagesList);
        List<Title> titlesPagination = Pagination.printResult((List<Title>) session.getAttribute("searchResult"), 0, paginationTotal);
        model.addAttribute("searchResult", titlesPagination);
        //
        return "search";
    }

    @PostMapping("/search")
    public String search(@RequestParam("search") String searchName, Model model, HttpSession session) {
        logger.info("Searching for \"" + searchName + "\"");
        List<Title> titlesList = titleService.getAll();
        List<Title> resultSearch = new ArrayList<>();
        for (Title title : titlesList) {
            if (title.getTitle().toLowerCase().contains(searchName.toLowerCase())) {
                resultSearch.add(title);
            }
        }
        session.setAttribute("searchResult", resultSearch);

        // pagination
        List<PagesBean> pagesList = Pagination.pagesCount(resultSearch, paginationTotal);
        session.setAttribute("countPagesSearch", pagesList);
        List<Title> titlesPagination = Pagination.printResult(resultSearch, 0, paginationTotal);
        model.addAttribute("searchResult", titlesPagination);
        //

        return "search";
    }

    @GetMapping("/search/page/{number}")
    public String getPageSearch(@PathVariable("number") int page, HttpSession session, Model model) {
        List<Title> titlesPagination = Pagination.printResult((List<Title>) session.getAttribute("searchResult"), page * paginationTotal - paginationTotal, paginationTotal);
        model.addAttribute("searchResult", titlesPagination);
        return "search";
    }

    @GetMapping("/ratings")
    public String getRating(Model model) {
        logger.info("Start showRating()...");
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
            logger.error("No register, see error fields.");
            return "register";
        }
        userService.save(user, city);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getLogin(), confirmPassword);
        authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("User " + user.getLogin() + " was register");
        return "redirect:/home";
    }

    @GetMapping("/showmessages")
    public String getMessages(Model model, Principal principal) {
        if (principal.getName().equals(adminLogin)) {
            List<AdminBuffer> adminBufferList = adminBufferService.getAll();
            List<Category> categoriesList = categoryService.getAll();
            List<SubCategory> subCategoriesList = subCategoryService.getAll();
            model.addAttribute("categoriesList", categoriesList);
            model.addAttribute("subCategoriesList", subCategoriesList);
            List<AdminBufferBean> bufferBeansList = new ArrayList<>();
            for (AdminBuffer adminBuffer : adminBufferList) {
                Map<String, Object> statusRequests = adminBufferService.getStatusRequests(adminBuffer.getId());
                Boolean isAdd = (Boolean) statusRequests.get("isAdd");
                Boolean cancelValue = (Boolean) statusRequests.get("cancel");
                String status = null;
                if (isAdd == false && cancelValue == false) {
                    status = "ADDED";
                }
                if (isAdd == true && cancelValue == false) {
                    status = "IN PROGRESS";
                }
                if (isAdd == false && cancelValue == true) {
                    status = "CANCEL";
                }
                AdminBufferBean buf = new AdminBufferBean(adminBuffer, status);
                bufferBeansList.add(buf);
            }
            model.addAttribute("adminBufferList", bufferBeansList);
            return "showadminmessages";
        } else {
            String userLogin = principal.getName();
            List<AdminBuffer> adminBufferByLogin = adminBufferService.getByUserLogin(userLogin);
            List<UserMessage> userMessageList = new ArrayList<>();
            for (AdminBuffer adminBuffer : adminBufferByLogin) {
                UserMessage userMessage = userMessageService.getByAdminBufferId(adminBuffer.getId());
                userMessageList.add(userMessage);
            }
            model.addAttribute("userMessageList", userMessageList);
            return "showusermessages";
        }
    }

    @PostMapping("/read")
    public String readMessage(@RequestParam("id") int idUserMessage, @RequestParam(value = "read", defaultValue = "") String readButton, HttpSession session, Principal principal) {
        if (readButton.equals("read" + idUserMessage)) {
            UserMessage userMessage = userMessageService.getById(idUserMessage);
            userMessage.setRead(true);
            userMessageService.save(userMessage);
            User currentUser = userService.getByLogin(principal.getName());
            session.setAttribute("messagesmenu", userMessageService.getCountNotReaded(currentUser));
        }
        return "redirect:/showmessages";
    }

    @GetMapping("/changecity")
    public String show(@RequestParam("city") String city, HttpSession session, HttpServletRequest request) {
        session.setAttribute("currentCity", city);
        String url = request.getHeader("referer");
        url = url.substring(url.indexOf("/") + 1);
        return "redirect:/" + url;
    }

    @GetMapping("/getphoto/{idTitle}")
    @ResponseBody
    public byte[] getPhoto(@PathVariable("idTitle") int idTitle) {
        return titleService.getById(idTitle).getPicture();
    }

    @GetMapping("/getphoto/admin/{idAdminBuffer}")
    @ResponseBody
    public byte[] getPhotoAdminPAge(@PathVariable("idAdminBuffer") int idAdminBuffer) {
        return adminBufferService.getById(idAdminBuffer).getTitlePicture();
    }

    @GetMapping("/404")
    public String getErrorNotFound() {
        return "404";
    }

    @GetMapping("/400")
    public String getBadRequest() {
        return "404";
    }

    @GetMapping("/403")
    public String getErrorAccessDenied() {
        return "403";
    }
}
