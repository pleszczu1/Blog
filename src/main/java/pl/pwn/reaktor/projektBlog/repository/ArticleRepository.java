package pl.pwn.reaktor.projektBlog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwn.reaktor.projektBlog.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>{

    Article findFirstByOrderByIdDesc();
}
