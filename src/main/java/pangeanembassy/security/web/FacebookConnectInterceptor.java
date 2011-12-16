package pangeanembassy.security.web;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;

@Component
public class FacebookConnectInterceptor extends
		EnsureUniqueConnectInterceptor<Facebook> {
	

}
