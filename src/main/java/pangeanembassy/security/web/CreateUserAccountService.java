package pangeanembassy.security.web;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import pangeanembassy.security.domain.User;

@Service
public class CreateUserAccountService {

	@Autowired
	private transient MailSender mailSender;

	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;
	
	private SimpleMailMessage simpleMailMessage;
	
	  
	public void sendMessage(String mailTo, String message) {
        simpleMailMessage.setTo(mailTo);
        simpleMailMessage.setText(message);
        mailSender.send(simpleMailMessage);
    }
	
	public void setDefaultRegistrationValues(UserRegistrationForm userRegistrationForm,UserProfile userProfile)
	{
		userRegistrationForm.setFirstName(userProfile.getFirstName());
		userRegistrationForm.setLastName(userProfile.getLastName());
		userRegistrationForm.setEmailAddress(userProfile.getEmail());
	}
	
	public void setDefaultRegistrationValues(SocialUserRegistrationForm userRegistrationForm,UserProfile userProfile)
	{
		userRegistrationForm.setEmailAddress(userProfile.getEmail());
	}
	
	private String generateDefaultPasswordForSocialSigninUser()
	{
		Random random = new Random(System.currentTimeMillis());
        String password = new Integer(random.nextInt()).toString();
        return password;
	}
	
	public User createUserAccount(SocialUserRegistrationForm socialUserRegistration,UserProfile userProfile)
	{
		UserRegistrationForm userRegistrationForm = new UserRegistrationForm();
		setDefaultRegistrationValues(userRegistrationForm,userProfile);
		userRegistrationForm.setPassword(generateDefaultPasswordForSocialSigninUser());
		if (socialUserRegistration.getEmailAddress() != null)
		{
			userRegistrationForm.setEmailAddress(socialUserRegistration.getEmailAddress());
		}
		return createUserAccount(userRegistrationForm);
	}
	
	
	public User createUserAccount(UserRegistrationForm userRegistration)
	{
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
        
		mailSender.send(mail);

		
		return User;
	}
	
}
