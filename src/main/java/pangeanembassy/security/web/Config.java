package pangeanembassy.security.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
public class Config {
	
	@Bean
	public ConnectionFactoryLocator connectionFactoryLocator() {
		ConnectionFactoryRegistry registry = connectionFactoryRegistry();
		
		registry.addConnectionFactory(new FacebookConnectionFactory(
				facebookClientId, facebookClientSecret));
		
		
		registry.addConnectionFactory(new TwitterConnectionFactory(
				twitterConsumerKey, twitterConsumerSecret));
		return registry;
	}

	@Bean
	public ConnectionFactoryRegistry connectionFactoryRegistry() {
		return new ConnectionFactoryRegistry();
	}
	

	@Value("${facebook.clientId}")
	private String facebookClientId;

	@Value("${facebook.clientSecret}")
	private String facebookClientSecret;
	

	@Value("${twitter.consumerKey}")
	private String twitterConsumerKey;

	@Value("${twitter.consumerSecret}")
	private String twitterConsumerSecret;


}
