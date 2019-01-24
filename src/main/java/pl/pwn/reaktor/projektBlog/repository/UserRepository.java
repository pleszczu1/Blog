package pl.pwn.reaktor.projektBlog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pwn.reaktor.projektBlog.model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user u inner join role r on u.role_id = r.id WHERE r.role=:name",nativeQuery = true)
    List<User> findUserWithRoleUserSQL(@Param("name") String roleName);

    // zapytnia hibernate HQL
    @Query("SELECT u FROM User u WHERE u.role.role=:name")
    List<User> findUserWithRoleUserHQL(@Param("name") String roleName);

    @Query("SELECT u FROM User u INNER JOIN Role r ON u.role.id=r.id WHERE r.role='user'")
    List<User> findUserWithRoleUserHQL2();
}
