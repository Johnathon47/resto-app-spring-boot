package components.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeathController {

    @GetMapping("/api/ping")
    public String pingpong(){
        return "pong";
    }
}
