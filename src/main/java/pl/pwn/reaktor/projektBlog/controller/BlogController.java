package pl.pwn.reaktor.projektBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.pwn.reaktor.projektBlog.service.ArticleService;

@Controller
public class BlogController {

    private ArticleService articleService;

    @Autowired
    public BlogController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(Model model){
        //ppobranie listy artykułów i doanie ich do modelu w celu wyświetlenia ich na stronie html
        model.addAttribute("articles", articleService.getAllArticle());

        return "index";
    }

    @GetMapping("/about")
    public String about(){
        return "about";
    }

}
