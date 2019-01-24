package pl.pwn.reaktor.projektBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pwn.reaktor.projektBlog.model.Tag;
import pl.pwn.reaktor.projektBlog.repository.TagRepository;

import java.util.List;

@Service
public class TagService {

    private TagRepository tagRepository;

    @Autowired

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    //metoda pobiera całą zawartość tabeli taga z bazy danych pry pomocy TagRepository
    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }
}
