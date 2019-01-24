package pl.pwn.reaktor.projektBlog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pwn.reaktor.projektBlog.model.Article;
import pl.pwn.reaktor.projektBlog.model.Tag;
import pl.pwn.reaktor.projektBlog.service.ArticleService;
import pl.pwn.reaktor.projektBlog.service.TagService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ArticleController {

    private TagService tagService;
    private ArticleService articleService;

    @Autowired
    public ArticleController(TagService tagService, ArticleService articleService) {
        this.tagService = tagService;
        this.articleService = articleService;
    }

    @GetMapping("/article")
    public  String article(Model model){

        //przekazujemy do modelu pusty obiekt article
        model.addAttribute("article", new Article());

        //tymczasowo p[odysawiamy również puste tagi
       // model.addAttribute("allTags", new Tag());
        //pobieramy wszsytkie tagi i wrzucamy do modelu, żeby były dostępne na stronie
        List<Tag> tags = tagService.getAllTags();
        model.addAttribute("allTags", tags);
        return "article/article";
    }

    @GetMapping("/article/{id}")
    public  String articleById(@PathVariable long id, Model model){

        Article article = articleService.getById(id);
        //przekazujemy do modelu pusty obiekt article
        model.addAttribute("article", article);

        return "article/single";
    }

    @PostMapping("/article")
    public String addArticle(@Valid @ModelAttribute Article article, BindingResult bindingResult, Model model) {
        //sprawdzamy czy formulaż jest prawidłowo wypełniony
        if (bindingResult.hasErrors()) {
            return "article/article";
        }
        //będziemy dodawali artykuł do bazy danych przez serwis i repository
        Article articleSaved = articleService.addArticle(article);
        //dodanie zapisanego artykułu do modelu w celu wyświtlenia go na stronie single
        model.addAttribute("article", articleSaved);

        return "article/single";
    }

    @GetMapping("/single")
    public String singleArticle(Model model){
        //dodajemy do modelu najnowszy artykuł z bazy danych
        Article article = articleService.getLastArticle();
        model.addAttribute("article", article);
        return "article/single";
    }
}
