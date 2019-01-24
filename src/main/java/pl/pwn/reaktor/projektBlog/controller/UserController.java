package pl.pwn.reaktor.projektBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pwn.reaktor.projektBlog.model.Role;
import pl.pwn.reaktor.projektBlog.model.User;
import pl.pwn.reaktor.projektBlog.repository.RoleRepository;
import pl.pwn.reaktor.projektBlog.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/users/{id}") //
    // pozwala pobrać wartość zmeinnej zadeklarowanej w klamrach
    public String view(@PathVariable(name = "id") long userId, Model model){
        User userById = userService.getUserById(userId);
        model.addAttribute("user",userById);
        //forder user a w nim strona html view.html która bedzie wyświetlała dane użytkownika
        return "user/view"; // wyświetlenie tylko html-a
    }

    @GetMapping("/users/delete/{id}") // adres strony w przeglądarce
    public String delete(@PathVariable(name = "id") long userId){
        userService.deleteUser(userId);
        //służy do kierowania na konkretny adres url
        return "redirect:/admin/config";
    }

    @GetMapping("/users/edit/{id}")
    public String edit(@PathVariable(name="id") long usedId, Model model){

        User userById = userService.getUserById(usedId);
        model.addAttribute("user", userById);
        List<Role> allRole = roleRepository.findAll();
        model.addAttribute("roles", allRole);
        return "user/edit";
    }

    @PostMapping("/edituser")
    public String userEdit(@Valid @ModelAttribute User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "user/edit";
        }
        userService.editUser(user);
        //przekierowanie na widok do wyświetlenia użytkownika którego zapisałiśmy
        return "redirect:/users/"+user.getId();
    }

}
