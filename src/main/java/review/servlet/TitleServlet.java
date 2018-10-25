package review.servlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import review.model.entity.AdminBuffer;
import review.model.entity.Title;
import review.service.*;
import review.servlet.beans.PagesBean;
import review.servlet.beans.TitlesBean;
import review.servlet.utils.Pagination;
import review.servlet.utils.PhotoUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TitleServlet {

    private static final Logger logger = Logger.getLogger(TitleServlet.class);

    @Autowired
    private TitleService titleService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private AdminBufferService adminBufferService;

    @Value("${pagination.size}")
    private int paginationTotal;

    @GetMapping({"/titles/subcat/{id}/{cityOption}", "/titles/subcat/{id}"})
    public String getTitle(@PathVariable("id") int idSubCategory, HttpSession session, Model model) {
        logger.info("Start getAllTitles()...");
        List<TitlesBean> titles = titleService.getBySubCategoryIdWithCity(idSubCategory);
        String currentCity = (String) session.getAttribute("currentCity");
        List<TitlesBean> buf = new ArrayList<>();
        if (currentCity != null && !currentCity.equals("All")) {
            for (TitlesBean title : titles) {
                if (title.getCity().equals(currentCity)) {
                    buf.add(title);
                }
            }
            session.setAttribute("titles", buf);
        } else {
            session.setAttribute("titles", titles);
        }
        model.addAttribute("idSubCategory", idSubCategory);

        // pagination
        List<PagesBean> pagesList = Pagination.pagesCount((List<Title>) session.getAttribute("titles"), paginationTotal);
        session.setAttribute("countPages", pagesList);
        List<Title> titlesPagination = Pagination.printResult((List<Title>) session.getAttribute("titles"), 0, paginationTotal);
        model.addAttribute("titles", titlesPagination);
        //
        return "titles";
    }

    @GetMapping("/title/{idSubCategory}/page/{number}")
    public String getPage(@PathVariable("number") int page, @PathVariable("idSubCategory") int idSubCategory, HttpSession session, Model model) {
        List<Title> titlesPagination = Pagination.printResult((List<Title>) session.getAttribute("titles"), page * paginationTotal - paginationTotal, paginationTotal);
        model.addAttribute("titles", titlesPagination);
        model.addAttribute("idSubCategory", idSubCategory);
        return "titles";
    }

    @GetMapping({"/titles/subcat/{idSubCat}/{currentCity}/addreviewtonewtitle", "/title/{idSubCat}/page/{number}/addreviewtonewtitle"})
    public String getAddReviewToNewTitle(@PathVariable("idSubCat") int idSubCat, Model model) {
        model.addAttribute("idSubCat", idSubCat);
        return "addreviewtonewtitle";
    }

    @PostMapping("/titles/subcat/{idSubCat}")
    public String addAdminBuffer(@PathVariable("idSubCat") int idSubCategory, Principal principal, RedirectAttributes redirectAttributes, Model model,
                                 @RequestParam("titleName") String titleName,
                                 @RequestParam("titleDescription") String titleDescription,
                                 @RequestParam("titleCity") String titleCity,
                                 @RequestParam(value = "file", required = false) Part file,
                                 @RequestParam("text") String reviewText,
                                 @RequestParam("reviewName") String reviewName,
                                 @RequestParam("mark") Integer mark) {
        if ((titleName == null || titleName.equals("")) || (titleCity == null || titleCity.equals("")) || (reviewName == null || reviewName.equals("")) || (reviewText == null || reviewText.equals("")) || (mark == null || mark == 0)) {
            logger.error("Required fields are empty.");
            model.addAttribute("errors", "review.empty");
            return "addreviewtonewtitle";
        }

        List<Title> titles = titleService.getByName(titleName);
        if (titles != null) {
            for (Title title : titles) {
                if (cityService.getById(title.getIdCity()).getName().equals(titleCity)) {
                    logger.error("This title already exist");
                    model.addAttribute("errors", "error.title.unique");
                    return "addreviewtonewtitle";
                }
            }
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
        adminBuffer.setTitlePicture(PhotoUtils.downloadPhoto(file));
        adminBuffer.setAdd(true);
        adminBufferService.save(adminBuffer);
        redirectAttributes.addFlashAttribute("userMessage", "You comment send to Admin");
        return "redirect:/titles/subcat/" + idSubCategory;
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
                            @RequestParam(value = "file", required = false) Part file,
                            @RequestParam("text") String reviewText,
                            @RequestParam("reviewName") String reviewName,
                            @RequestParam("mark") Integer mark) {
        if ((titleName == null || titleName.equals("")) || (titleCity == null || titleCity.equals("")) || (reviewName == null || reviewName.equals("")) || (reviewText == null || reviewText.equals("")) || (mark == null || mark == 0) || (categoryName == null || categoryName.equals("")) || (subCategoryName == null || subCategoryName.equals(""))) {
            logger.error("Required fields are empty.");
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
        adminBuffer.setTitlePicture(PhotoUtils.downloadPhoto(file));
        adminBuffer.setAdd(true);
        adminBufferService.save(adminBuffer);
        redirectAttributes.addFlashAttribute("userMessage", "You comment send to Admin");
        return "redirect:/home";
    }

}
