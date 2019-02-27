package review;

import org.springframework.web.client.RestTemplate;
import review.model.entity.User;

public class Test {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String user = restTemplate.getForObject("http://localhost:8080/users/{userName}", String.class, "panya");
//        System.out.println(user.getLogin() + " (" + user.getEmail() + ")");
        System.out.println(user);

    }

}
