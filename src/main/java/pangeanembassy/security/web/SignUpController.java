package pangeanembassy.security.web;

import java.util.Date;
import java.util.Random;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import pangeanembassy.security.domain.User;

@RequestMapping("/signup/**")
@Controller
public class SignUpController {

    @Autowired
    private SignUpValidator validator;

    @Autowired
    private transient MailSender mailSender;

    private transient SimpleMailMessage simpleMailMessage;
    
    @Autowired
    private transient SignInAdapter signInService;

	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

    @ModelAttribute("User")
    public UserRegistrationForm formBackingObject() {
        return new UserRegistrationForm();
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model,WebRequest request) {
    	UserRegistrationForm form = new UserRegistrationForm();
    	Connection<?> connection = ProviderSignInUtils.getConnection(request);

    	if (connection != null)
		{
		UserProfile userProfile = connection.fetchUserProfile();
		form.setFirstName(userProfile.getFirstName());
		form.setLastName(userProfile.getLastName());
		form.setEmailAddress(userProfile.getEmail());


		}
    	
    	
    	
    	
    	
        model.addAttribute("User", form);
        model.addAttribute("captcha_form",form.getReCaptchaHtml());
        return "signup/index";
    }
    
    @RequestMapping(params = "activate", method = RequestMethod.GET)
    public String activateUser(@RequestParam(value = "activate", required = true) String activationKey,@RequestParam(value = "emailAddress", required = true) String emailAddress,Model model) {
        TypedQuery<User> query = User.findUsersByActivationKeyAndEmailAddress(activationKey, emailAddress);
        User User=query.getSingleResult();
        if(null!=User){
        	User.setActivationDate(new Date());
        	User.setEnabled(true);
        	User.merge();
        	return "login";
        }
        else{
        	return "signup/error";
        }

    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(WebRequest webRequest,@Valid UserRegistrationForm userRegistration, BindingResult result, Model model, HttpServletRequest request) {
        validator.validate(userRegistration, result);
        if (result.hasErrors()) {
        	
            return createForm(model,webRequest);
        } else {
            Random random = new Random(System.currentTimeMillis());
            String activationKey = "activationKey:" + random.nextInt();

            User User = new User();
            User.setActivationDate(null);
            User.setEmailAddress(userRegistration.getEmailAddress());
            User.setFirstName(userRegistration.getFirstName());
            User.setLastName(userRegistration.getLastName());
            User.setPassword(messageDigestPasswordEncoder.encodePassword(userRegistration.getPassword(), null));
            User.setActivationKey(activationKey);
            User.setEnabled(false);
            User.setLocked(false);
            User.persist();
            
            SimpleMailMessage mail = new SimpleMailMessage();
    		mail.setTo(User.getEmailAddress());
    		mail.setSubject("User Activaton");
    		
    		mail.setText("Hi "+User.getFirstName()+",\n. You had registered with us. Please click on this link to activate your account - <a href=\"http://__BASE_URL__/signup?emailAddress="+User.getEmailAddress()+"&activate="+activationKey+"\">Activate Link</a>. \n Thanks Tyical Security Admin");
            
    		
			Connection<?> connection = ProviderSignInUtils.getConnection(new ServletWebRequest(request));
			String userId = new Long(User.getId()).toString();
			ProviderSignInUtils.handlePostSignUp(userId, new ServletWebRequest(request));
			signInService.signIn(userId, connection, new ServletWebRequest(request));
    		
    		
    		mailSender.send(mail);
            return "signup/thanks";
        }
    }

    @RequestMapping
    public String index() {
        return "signup/index";
    }

    @RequestMapping
    public String thanks() {
        return "signup/thanks";
    }
    @RequestMapping
    public String error() {
        return "signup/error";
    }

    public void sendMessage(String mailTo, String message) {
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
    }
}
