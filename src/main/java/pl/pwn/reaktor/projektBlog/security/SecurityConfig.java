package pl.pwn.reaktor.projektBlog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        auth.jdbcAuthentication()
                // sprawdzenie i pobranie loginu i hasła oraz czy jest aktywny użytkownika po adrssei email
                //który jest u nas w systemie loginem
                .usersByUsernameQuery("SELECT email, password, active FROM user WHERE email=?")
                //pobranie roli użytkownika z bazy danych po podanym loginie
                .authoritiesByUsernameQuery("SELECT u.email, r.role FROM user u inner join role r ON r.id=u.role_id WHERE u.email=?")
                //ustawienie klasy odpowiedzilanej za nawiąznie połączenia do bazy danych
                .dataSource(dataSource)
                //ustawienie sposobu kodowania hasła w bazie danych
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //dostęp do adresów /admin będzie miał tylko użytkownik o roli admin
                .antMatchers("/admin/*","/users/*").hasAuthority("admin")
                //poniższe są dostępne dla user i admin
               // .antMatchers("/article/*","/article").hasAnyAuthority("user", "admin")
                //poniższe są dostępne dla wszsytkich zalogowanych bez wzgledu na role
                // .antMatchers("/article/*","/article").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")//adres strony z naszym formularzem logowania
                .defaultSuccessUrl("/")//adres strony po poprawnym zalogowaniu
                .failureUrl("/login?error=true")//adres gdy błędne logowanie
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }
}
