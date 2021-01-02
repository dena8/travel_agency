package final_project.travel_agency.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getHome(){
        return "home";
    }

    @GetMapping("/user_home")
    public String getUserHome(){
        return "user_home";
    }

    @GetMapping("/admin")
    public String getAdminHome(){
        return "admin";
    }

}
