package pangeanembassy.security.web;

import java.util.Date;
import java.util.Random;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import pangeanembassy.security.domain.User;

@RequestMapping("/forgotpassword/**")
@Controller
public class ForgotPasswordController {

    @Autowired
    private transient MailSender mailSender;

    private transient SimpleMailMessage simpleMailMessage;

	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

    @ModelAttribute("forgotpasswordForm")
    public ForgotPasswordForm formBackingObject() {
        return new ForgotPasswordForm();
    }

    @RequestMapping
    public String index() {
        return "forgotpassword/index";
    }

    @RequestMapping
    public String thanks() {
        return "forgotpassword/thanks";
    }

    @RequestMapping(value = "/forgotpassword/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("forgotpasswordForm") ForgotPasswordForm form, BindingResult result) {
        if (result.hasErrors()) {
        	return "forgotpassword/index";
        } else {
        	TypedQuery<User> userQuery=User.findUsersByEmailAddress(form.getEmailAddress());
        	if(null!=userQuery && userQuery.getMaxResults()>0){
        		User User = userQuery.getSingleResult();
        		Random random = new Random(System.currentTimeMillis());
        		String newPassword = "pass"+random.nextLong();
        		User.setPassword(messageDigestPasswordEncoder.encodePassword(newPassword, null));
        		User.merge();
        		SimpleMailMessage mail = new SimpleMailMessage();
        		mail.setTo(form.getEmailAddress());
        		mail.setSubject("Password Recover");
        		mail.setText("Hi "+User.getFirstName()+",\n. You had requested for password recovery. Your password is "+newPassword+". \n Thanks Tyical Security Admin");
        		mailSender.send(mail);
        	}

            return "forgotpassword/thanks";
        }
    }

    public void sendMessage(String mailTo, String message) {
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
    }
}
