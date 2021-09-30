package guru.springfamework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @GetMapping("/")
    public String swaggerUi() {
        return "redirect:/swagger-ui/";
    }
}
