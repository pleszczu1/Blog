package pl.pwn.reaktor.projektBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pwn.reaktor.projektBlog.model.Contact;
import pl.pwn.reaktor.projektBlog.service.EmailServices;

import javax.validation.Valid;

@Controller
public class ContactController {

    private EmailServices emailServices;

    @Autowired
    public ContactController(EmailServices emailServices) {
        this.emailServices = emailServices;
    }

    @GetMapping("/contact")
    public String contact(Model model){

        Contact contact = new Contact();
        model.addAttribute("contact",contact);
        return "contact";
    }

    @PostMapping("/sentMessage")
    public String send(@Valid @ModelAttribute Contact contact, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "contact";
        }
        emailServices.sendMessage(contact);
        model.addAttribute("contact",new Contact());
        return "contact";
    }
}
