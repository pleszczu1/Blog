package pl.pwn.reaktor.projektBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.pwn.reaktor.projektBlog.model.User;
import pl.pwn.reaktor.projektBlog.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/config")
    public String config(Model model){

        List<User> allUser = userService.getUserWithRoleUser();
        model.addAttribute("users", allUser);
        return "admin/config";
    }
}
