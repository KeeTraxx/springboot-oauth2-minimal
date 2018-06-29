package ch.puzzle.oauth2.example.oauth2;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @GetMapping("/info")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")
    public Principal userInfo(Principal p) {
        System.out.println(p);
        return p;
    }
}
