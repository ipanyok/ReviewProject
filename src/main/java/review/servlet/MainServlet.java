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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import review.model.entity.*;
import review.service.*;
import review.servlet.beans.AdminBufferBean;
import review.servlet.beans.PagesBean;
import review.servlet.beans.ReviewsBean;
import review.servlet.beans.TitlesBean;
import review.servlet.utils.CategoriesUtils;
import review.servlet.utils.Pagination;
import review.servlet.utils.RatingUtils;
import review.servlet.utils.validator.UserValidator;

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

    private final String ALL_TITLES_FROM_CATEGORY = "titles";

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
            if (!principal.getName().equals(adminLogin)) {
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
        logger.info("Start getAllTitles()...");
        List<TitlesBean> titles = titleService.getBySubCategoryIdWithCity(idSubCategory);
        session.setAttribute(ALL_TITLES_FROM_CATEGORY, titles);
        model.addAttribute("idSubCategory", idSubCategory);

        // pagination
        List<PagesBean> pagesList = Pagination.pagesCount((List<Title>) session.getAttribute(ALL_TITLES_FROM_CATEGORY), paginationTotal);
        session.setAttribute("countPages", pagesList);
        List<Title> titlesPagination = Pagination.printResult((List<Title>) session.getAttribute(ALL_TITLES_FROM_CATEGORY), 0, paginationTotal);
        model.addAttribute(ALL_TITLES_FROM_CATEGORY, titlesPagination);
        //
        return "titles";
    }

    @GetMapping("/title/{idSubCategory}/page/{number}")
    public String getPage(@PathVariable("number") int page, @PathVariable("idSubCategory") int idSubCategory, HttpSession session, Model model) {
        List<Title> titlesPagination = Pagination.printResult((List<Title>) session.getAttribute(ALL_TITLES_FROM_CATEGORY), page * paginationTotal - paginationTotal, paginationTotal);
        model.addAttribute(ALL_TITLES_FROM_CATEGORY, titlesPagination);
        model.addAttribute("idSubCategory", idSubCategory);
        return "titles";
    }

    @GetMapping("/search/page/{number}")
    public String getPageSearch(@PathVariable("number") int page, HttpSession session, Model model) {
        List<Title> titlesPagination = Pagination.printResult((List<Title>) session.getAttribute("searchResult"), page * paginationTotal - paginationTotal, paginationTotal);
        model.addAttribute("searchResult", titlesPagination);
        return "search";
    }

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
        if (bindingResult.hasErrors()) {
            model.addAttribute("idTitle", review.getIdTitle());
            return "addreview";
        }
        User currentUser = userService.getByLogin(principal.getName());
        review.setIdUser(currentUser.getId());
        reviewService.save(review);
        logger.info("Review was added");
        return "redirect:/titles/" + review.getIdTitle();
    }

    @GetMapping({"/titles/subcat/{idSubCat}/addreviewtonewtitle", "/title/{idSubCat}/page/{number}/addreviewtonewtitle"})
    public String getAddReviewToNewTitle(@PathVariable("idSubCat") int idSubCat, Model model) {
        model.addAttribute("idSubCat", idSubCat);
        return "addreviewtonewtitle";
    }

    @PostMapping("/titles/subcat/{idSubCat}")
    public String addAdminBuffer(@PathVariable("idSubCat") int idSubCategory, Principal principal, RedirectAttributes redirectAttributes, Model model,
                                 @RequestParam("titleName") String titleName,
                                 @RequestParam("titleDescription") String titleDescription,
                                 @RequestParam("titleCity") String titleCity,
                                 @RequestParam("text") String reviewText,
                                 @RequestParam("reviewName") String reviewName,
                                 @RequestParam("mark") Integer mark) {
        if ((titleName == null || titleName.equals("")) || (titleCity == null || titleCity.equals("")) || (reviewName == null || reviewName.equals("")) || (reviewText == null || reviewText.equals("")) || mark == null) {
            model.addAttribute("errors", "review.empty");
            return "addreviewtonewtitle";
        }

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

    @GetMapping("/addtitle")
    public String getAddItem(Model model) {
        List<Category> categoryList = categoryService.getAll();
        List<SubCategory> subCategoryList = subCategoryService.getAll();
        model.addAttribute("categories", categoryList);
        model.addAttribute("subCategories", subCategoryList);
        return "addtitle";
    }

    @PostMapping("/addtitle")
    public String addItem(@RequestParam("categories") String categoryName,
                          @RequestParam("subCategories") String subCategoryName,
                          @RequestParam("title") String titleName,
                          @RequestParam("cities") String titleCity,
                          @RequestParam("titleDescription") String titleDescription,
                          HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        if ((titleName == null || titleName.equals("")) || (titleCity == null || titleCity.equals(""))) {
            model.addAttribute("titleerror", "title.null");
            return getAddItem(model);
        }

        if ((categoryName == null || categoryName.equals("")) || (subCategoryName == null || subCategoryName.equals(""))) {
            model.addAttribute("categoryerror", "title.nullcategory");
            return getAddItem(model);
        }

        Category category;
        SubCategory subCategory = subCategoryService.getByName(subCategoryName);
        if (subCategory == null) {
            category = categoryService.getByName(categoryName);
            if (category == null) {
                category = new Category(categoryName);
                categoryService.save(category);
            }
            subCategory = new SubCategory(category.getId(), subCategoryName);
            subCategoryService.save(subCategory);
        }
        List<City> citiesList = cityService.getAll();
        boolean isCityExist = false;
        for (City city : citiesList) {
            if (city.getName().equals(titleCity)) {
                isCityExist = true;
                break;
            }
        }
        if (!isCityExist) {
            City city = new City(titleCity);
            cityService.save(city);
            List<String> cities = (List<String>) session.getAttribute("cities");
            cities.add(city.getName());
            session.setAttribute("cities", cities);
        }
        Title title = new Title(subCategory.getId(), titleName, titleDescription, cityService.getByName(titleCity).getId());
        titleService.save(title, titleCity);

        // Add category in category map
        Map<String, List<SubCategory>> categoryMap = CategoriesUtils.addCategoryInMap(session, categoryName, subCategory, categoryMapAttribute);

        redirectAttributes.addFlashAttribute("success", "title.success");
        session.setAttribute(categoryMapAttribute, categoryMap);
        logger.info("Title " + titleName + " was create");
        return "redirect:/addtitle";
    }

    @GetMapping("/addcategories")
    public String getAddCategories(Model model) {
        List<Category> categoryList = categoryService.getAll();
        model.addAttribute("categories", categoryList);
        return "addcategories";
    }

    @PostMapping("/addcategories")
    public String addCategories(@RequestParam("categories") String categoryName, @RequestParam("subCategories") String subCategoryName, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        if ((categoryName == null || categoryName.equals("")) && (subCategoryName == null || subCategoryName.equals(""))) {
            model.addAttribute("error", "error.nullcategories");
            return getAddCategories(model);
        }
        if ((subCategoryName == null || subCategoryName.equals("")) && categoryName != null) {
            List<Category> categoryList = categoryService.getAll();
            for (Category category : categoryList) {
                if (category.getName().equals(categoryName)) {
                    model.addAttribute("errorexist", "error.categoryexist");
                    return getAddCategories(model);
                }
            }
            Category category = new Category(categoryName);
            categoryService.save(category);

            Map<String, List<SubCategory>> categoryMap = (Map<String, List<SubCategory>>) session.getAttribute(categoryMapAttribute);
            if (categoryMap != null) {
                categoryMap.put(categoryName, null); // возможно надо создать пустой arraylist
            } else {
                categoryMap = new TreeMap<>();
                categoryMap.put(categoryName, null); // возможно надо создать пустой arraylist
            }

            redirectAttributes.addFlashAttribute("success", "category.success");
            session.setAttribute(categoryMapAttribute, categoryMap);
        }
        if (subCategoryName != null && !subCategoryName.equals("")) {
            if (categoryName == null || categoryName.equals("")) {
                model.addAttribute("nocategory", "category.null");
                return getAddCategories(model);
            }
            List<SubCategory> subCategories = subCategoryService.getAll();
            for (SubCategory subCategory : subCategories) {
                if (subCategory.getName().equals(subCategoryName)) {
                    model.addAttribute("subcategoryexist", "subcategory.exist");
                    return getAddCategories(model);
                }
            }

            List<Category> categoryList = categoryService.getAll();
            boolean isExist = false;
            for (Category category : categoryList) {
                if (category.getName().equals(categoryName)) {
                    isExist = true;
                }
            }
            if (isExist) {
                SubCategory subCategory = new SubCategory(categoryService.getByName(categoryName).getId(), subCategoryName);
                subCategoryService.save(subCategory);
                redirectAttributes.addFlashAttribute("success", "category.success");

                // Add in category map
                Map<String, List<SubCategory>> categoryMap = CategoriesUtils.addCategoryInMap(session, categoryName, subCategory, categoryMapAttribute);

                session.setAttribute(categoryMapAttribute, categoryMap);
            } else {
                Category newCategory = new Category(categoryName);
                categoryService.save(newCategory);
                SubCategory subCategory = new SubCategory(categoryService.getByName(categoryName).getId(), subCategoryName);
                subCategoryService.save(subCategory);
                redirectAttributes.addFlashAttribute("success", "category.success");

                Map<String, List<SubCategory>> categoryMap = (Map<String, List<SubCategory>>) session.getAttribute("categoryMap");
                if (categoryMap != null) {
                    categoryMap.put(categoryName, new ArrayList<>(Arrays.asList(subCategory)));
                } else {
                    categoryMap = new TreeMap<>();
                    categoryMap.put(categoryName, new ArrayList<>(Arrays.asList(subCategory)));
                }

                session.setAttribute(categoryMapAttribute, categoryMap);
            }
        }
        logger.info("Category " + categoryName + " was created");
        return "redirect:/addcategories";
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

    @PostMapping("/newcategory")
    public String addNewUserCategory(@RequestParam(value = "add", defaultValue = "") String add,
                                     @RequestParam(value = "cancel", defaultValue = "") String cancel,
                                     @RequestParam("idAdminBuffer") Integer idAdminBuffer,
                                     @RequestParam("categoryName") String categoryName,
                                     @RequestParam("subCategoryName") String subCategoryName,
                                     @RequestParam("titleName") String titleName,
                                     @RequestParam("titleCity") String titleCity,
                                     @RequestParam("titleDescription") String titleDescription,
                                     @RequestParam("reviewName") String reviewName,
                                     @RequestParam("reviewText") String reviewText,
                                     @RequestParam("mark") int mark,
                                     @RequestParam("userName") String userName,
                                     HttpSession session) {
        if (add.equals("add" + idAdminBuffer)) {
            AdminBuffer adminBuffer = adminBufferService.getById(idAdminBuffer);
            adminBuffer.setAdd(false);
            adminBuffer.setCancel(false);
            adminBufferService.save(adminBuffer);
            session.setAttribute("messagesmenu", adminBufferService.getCountFromUsers());

            Title title = new Title();
            title.setIdSubCategory(subCategoryService.getByName(subCategoryName).getId());
            title.setTitle(titleName);
            title.setDescription(titleDescription);
            titleService.save(title, titleCity);
            Review review = new Review(title.getId(), userService.getByLogin(userName).getId(), reviewText, mark, reviewName);
            reviewService.save(review);

            // add message to user
            String adminMessage = "Your review \"" + reviewName + "\" to \"" + titleName + "(" + titleCity + ")\" successfully added in " + categoryName + " -> " + subCategoryName + ".";
            UserMessage userMessage = new UserMessage(idAdminBuffer, adminMessage, false);
            userMessageService.save(userMessage);
            logger.info("Review was added");

            return "redirect:/showmessages";
        }
        if (cancel.equals("cancel" + idAdminBuffer)) {
            AdminBuffer adminBuffer = adminBufferService.getById(idAdminBuffer);
            adminBuffer.setAdd(false);
            adminBuffer.setCancel(true);
            adminBufferService.save(adminBuffer);
            session.setAttribute("messagesmenu", adminBufferService.getCountFromUsers());

            // add message to user
            String adminMessage = "Your review \"" + reviewName + "\" to \"" + titleName + "(" + titleCity + ")\" is cancel!";
            UserMessage userMessage = new UserMessage(idAdminBuffer, adminMessage, false);
            userMessageService.save(userMessage);
            logger.info("Review was cancelled");

            return "redirect:/showmessages";
        }
        return "home";
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

    @GetMapping("/addallnew")
    public String getAddAll() {
        return "addallnew";
    }

    @PostMapping("/addallnew")
    public String addAllNew(@RequestParam("categoryName") String categoryName, Principal principal, RedirectAttributes redirectAttributes, Model model,
                            @RequestParam("subCategoryName") String subCategoryName,
                            @RequestParam("titleName") String titleName,
                            @RequestParam("titleDescription") String titleDescription,
                            @RequestParam("titleCity") String titleCity,
                            @RequestParam("text") String reviewText,
                            @RequestParam("reviewName") String reviewName,
                            @RequestParam("mark") Integer mark) {
        if ((titleName == null || titleName.equals("")) || (titleCity == null || titleCity.equals("")) || (reviewName == null || reviewName.equals("")) || (reviewText == null || reviewText.equals("")) || mark == null || (categoryName == null || categoryName.equals("")) || (subCategoryName == null || subCategoryName.equals(""))) {
            model.addAttribute("errors", "review.empty");
            return "addallnew";
        }

        AdminBuffer adminBuffer = new AdminBuffer();
        adminBuffer.setUserName(userService.getByLogin(principal.getName()).getLogin());
        adminBuffer.setCategoryName(categoryName);
        adminBuffer.setSubCategoryName(subCategoryName);
        adminBuffer.setTitleName(titleName);
        adminBuffer.setTitleDescription(titleDescription);
        adminBuffer.setTitleCity(titleCity);
        adminBuffer.setReviewText(reviewText);
        adminBuffer.setReviewName(reviewName);
        adminBuffer.setMark(mark);
        adminBuffer.setAdd(true);
        adminBufferService.save(adminBuffer);
        redirectAttributes.addFlashAttribute("userMessage", "You comment send to Admin");
        return "redirect:/home";
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

}
