package pl.pwn.reaktor.projektBlog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pwn.reaktor.projektBlog.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
