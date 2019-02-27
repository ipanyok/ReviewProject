package review.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import review.model.entity.Title;
import review.model.entity.User;
import review.service.TitleService;
import review.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RESTServlet {

    @Autowired
    private TitleService titleService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/titles/subcategory/{titleName}", method = RequestMethod.GET, headers = "Accept=text/xml, text/html, application/json")
    public List<Title> getTitleByName(@PathVariable("titleName") String titleName) {
        System.out.println("Start get title by name: " + titleName);
        return titleService.getByName(titleName);
    }

    @ResponseBody
    @RequestMapping(value = "/users/{userName}", method = RequestMethod.GET, headers = {"Accept=application/json, text/xml, text/html"})
    public User getUserByName(@PathVariable("userName") String userName) {
        System.out.println("Start get user by login: " + userName);
        return userService.getByLogin(userName);
    }

}