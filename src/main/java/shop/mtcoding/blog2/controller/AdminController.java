package shop.mtcoding.blog2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping({ "/admin", "/admin/user" })
    public String user() {
        return "admin/user";
    }

    @GetMapping("/admin/board")
    public String board() {
        return "admin/board";
    }

    @GetMapping("/admin/reply")
    public String reply() {
        return "admin/reply";
    }

    @GetMapping("/admin/loginForm")
    public String loginForm() {
        return "admin/loginForm";
    }
}
