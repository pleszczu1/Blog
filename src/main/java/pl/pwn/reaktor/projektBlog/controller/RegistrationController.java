package pl.pwn.reaktor.projektBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pwn.reaktor.projektBlog.model.User;
import pl.pwn.reaktor.projektBlog.service.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    //Pole o typie UserService nazwa serwisu i nazwa pola
    private UserService userService;

    //konstruktor
    @Autowired
    public RegistrationController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration(Model model){

        //inicjalizojemy obiekt user żeby można było go uzupwłnić na formularzu danymi
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String save(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            // wyświetlenie strony gdy znajdziemy jakieś błędy walidacyjne
            return "registration";
        }

        // chcemy zapisać użtkownika do bazy danych?
        //do tego potrzebujemy serwisu który wywola przy pomocy repository zapis do bazy danych
        User userSaved = userService.addUserWithRoleUser(user);
        System.out.println("Saved user: "+ userSaved);

        //wyczyszczenie formularza, gdy pzostajemy na tym samym widoku
        User user1 = new User();
        model.addAttribute("success", "User has been registered successfully");
        model.addAttribute("user", user1);

        return "registration";
    }
}
