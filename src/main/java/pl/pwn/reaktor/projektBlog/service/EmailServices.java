package pl.pwn.reaktor.projektBlog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.pwn.reaktor.projektBlog.model.Contact;

@Component
public class EmailServices {

    private static final String TO = "pleszczu@gmail.com";
    private static final String SUBJECT = "Contact form from Blog";

    @Autowired
    public JavaMailSender mailSender;

    public void sendMessage(Contact contact){

        System.out.println("Send message: " + contact);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(TO);
        message.setSubject(SUBJECT);
        message.setText(contact.getMessage()+"\n"+contact.getPhone());
        message.setReplyTo(contact.getEmail());

        try {
            mailSender.send(message);
        }catch (MailException e){
            e.printStackTrace();
        }
    }
}
