package pangeanembassy.security.web;

import org.socialsignin.springframework.social.security.signin.SpringSocialSecurityConnectInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.Connection;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class TwitterConnectInterceptor extends
		EnsureUniqueConnectInterceptor<Twitter> {
	

}
