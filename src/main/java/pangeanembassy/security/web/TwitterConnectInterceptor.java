package pangeanembassy.security.web;

import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Component;

@Component
public class TwitterConnectInterceptor extends
		EnsureUniqueConnectInterceptor<Twitter> {
	

}
