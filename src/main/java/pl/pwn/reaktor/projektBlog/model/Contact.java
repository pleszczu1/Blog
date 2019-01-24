package pl.pwn.reaktor.projektBlog.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Contact {

    @NotEmpty(message = "{contact.notempty}")
    private String name;

    @NotEmpty(message = "{contact.notempty}" )
    @Email(message = "{contact.email}")
    private String email;

    @Pattern(regexp = "^[0-9]*$", message = "{contact.phone.pattern}")
    private String phone;

    @NotEmpty(message = "{contact.notempty}" )
    @Length(min = 10, max = 100, message = "{contact.message.lenght}")
    private String message;

    public Contact() {
    }

    public Contact(String name, String email, String phone, String message) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
