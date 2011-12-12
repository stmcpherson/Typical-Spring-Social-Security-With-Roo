package pangeanembassy.security.provider;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SpringSocialAuthenticationToken extends
		UsernamePasswordAuthenticationToken {

	public SpringSocialAuthenticationToken(String userName) {
		super(userName,"password");
	}

}
