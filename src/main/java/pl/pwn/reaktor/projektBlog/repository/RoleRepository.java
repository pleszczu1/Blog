package pl.pwn.reaktor.projektBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pwn.reaktor.projektBlog.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(String role);

}
