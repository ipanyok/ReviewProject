package review.servlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import review.model.entity.*;
import review.service.*;
import review.servlet.utils.CategoriesUtils;
import review.servlet.utils.PhotoUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.*;

@Controller
public class AdminServlet {

    private static final Logger logger = Logger.getLogger(AdminServlet.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TitleService titleService;

    @Autowired
    private AdminBufferService adminBufferService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMessageService userMessageService;

    @Autowired
    private ReviewService reviewService;

    @Value("${categories.attribute}")
    private String categoryMapAttribute;

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
                          @RequestParam(value = "file", required = false) Part file,
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
        title.setPicture(PhotoUtils.downloadPhoto(file));

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

            if (subCategoryService.getByName(subCategoryName) == null || (categoryService.getByName(categoryName) == null && subCategoryService.getByName(subCategoryName) != null)) {
                if (categoryService.getByName(categoryName) == null) {
                    Category newCategory = new Category(categoryName);
                    categoryService.save(newCategory);
                }
                SubCategory newSubCategory = new SubCategory(categoryService.getByName(categoryName).getId(), subCategoryName);
                subCategoryService.save(newSubCategory);
            }

            title.setIdSubCategory(subCategoryService.getByName(subCategoryName).getId());
            title.setTitle(titleName);
            title.setDescription(titleDescription);
            title.setPicture(adminBuffer.getTitlePicture());
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

}
