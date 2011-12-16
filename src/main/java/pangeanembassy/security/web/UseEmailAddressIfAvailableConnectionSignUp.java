package pangeanembassy.security.web;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Component;

import pangeanembassy.security.domain.User;

@Component
public class UseEmailAddressIfAvailableConnectionSignUp implements ConnectionSignUp {


    @Autowired
    private CreateUserAccountService createUserAccountService;
	
	@Override
	public String execute(Connection<?> connection) {
		
		
			SocialUserRegistrationForm form = new SocialUserRegistrationForm();
			UserProfile userProfile = connection.fetchUserProfile();
			if (userProfile.getEmail() != null)
			{
				User User = createUserAccountService.createUserAccount(form,userProfile);
			
			return new Long(User.getId()).toString();
			
		}
		else
		{
			return null;
		}
	}

}
