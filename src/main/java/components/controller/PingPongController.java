package components.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PingPongController {

    @GetMapping("/ping")
    public String pingPong(Model model) {
        model.addAttribute("pong");
        return "pong";
    }
}
