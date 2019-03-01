package review.service.soapservice;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import review.model.entity.User;
import review.service.UserService;

import java.util.List;

@Endpoint
public class UserServiceSOAP {

    private static final Logger logger = Logger.getLogger(UserServiceSOAP.class);

    @Autowired
    private UserService userService;

    private final String NAMESPACE = "http://service.review/soapservice";

    @PayloadRoot(namespace = NAMESPACE, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUserByLogin(@RequestPayload GetUserRequest request) {
        logger.info("Start SOAP service. Get by login");
        String userLogin = request.getLogin();
        User user = userService.getByLogin(userLogin);
        review.service.soapservice.User userRemote = new review.service.soapservice.User();
        userRemote.setEmail(user.getEmail());
        userRemote.setFirstName(user.getFirstName());
        userRemote.setLastName(user.getLastName());
        userRemote.setLogin(user.getLogin());
        GetUserResponse response = new GetUserResponse();
        response.setUser(userRemote);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE, localPart = "getAllUserRequest")
    @ResponsePayload
    public GetAllUserResponse getAllUsers() {
        logger.info("Start SOAP service. Get all users");
        List<User> allUsersList = userService.getAll();
        GetAllUserResponse responce = new GetAllUserResponse();
        for (User user : allUsersList) {
            review.service.soapservice.User userRemote = new review.service.soapservice.User();
            userRemote.setLogin(user.getLogin());
            userRemote.setLastName(user.getLastName());
            userRemote.setFirstName(user.getFirstName());
            userRemote.setEmail(user.getEmail());
            responce.getUserList().add(userRemote);
        }
        return responce;
    }

}
