package pl.pwn.reaktor.projektBlog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pwn.reaktor.projektBlog.model.Role;
import pl.pwn.reaktor.projektBlog.model.User;
import pl.pwn.reaktor.projektBlog.repository.RoleRepository;
import pl.pwn.reaktor.projektBlog.repository.UserRepository;

import javax.jws.soap.SOAPBinding;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    // nazwy pola tworzone z repository w klasie Service
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    // konstruktor
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    //metody
    public User addUserWithRoleUser(User user){
        //aktyewujemy użytkowniaka przed zapisem do bazy danych
        user.setActive(true);

        //kodowanie hasła użytkownika przed zapisem do bazy danych
        PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        //ustawienie roli user dla użytkownika
        Role role = roleRepository.findByRole("user");
        if(role == null){
            // jeżeli rola user nie istnieje to ją tworzywmy i robimy zapis do bazy
            Role roleTOSave = new Role("user");
            role = roleRepository.save(roleTOSave);

        }
        //po utworzeniu i sprawdzeniu czy rola istnieje ustawiamy ją dla użytkownika
        user.setRole(role);

        //zapis usera do bazy danych
        User saveUser = userRepository.save(user);
        return saveUser;

    }

    public List<User> getAllUser(){
       List<User> users =  userRepository.findAll(Sort.by(Sort.Order.desc("role.role"),Sort.Order.asc("email")));
        return users;
    }

    public List<User> getUserWithRoleUser(){
        List<User> onlyUsers = userRepository.findUserWithRoleUserSQL("user");
        return onlyUsers;
    }

    public User getUserById(long userId){
        Optional<User> optionalUser = userRepository.findById(userId); //optional zabezpiecza przed NullPointerException, albo jest albo nie
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        return null;
    }

    public void deleteUser(long userId){
        userRepository.deleteById(userId);
    }

    public User editUser(User user){
        // pobieramy użytkownika z bazy danych w celu pobrania z niego hasła
        //ponieważ na formularzu nie mamy zmieny hasła
        User userFromDB = getUserById(user.getId());
        //ustawienei hasła z bazy danych do formularza
        user.setPassword(userFromDB.getPassword());
        //zapisanie użytkowniak do bazy danych
        User saveUser = userRepository.save(user);
        return saveUser;
    }

}
