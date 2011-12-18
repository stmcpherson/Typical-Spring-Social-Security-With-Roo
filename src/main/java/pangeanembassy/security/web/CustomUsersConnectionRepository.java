package pangeanembassy.security.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.socialsignin.springframework.social.security.connect.jdbc.SpringSocialSecurityJdbcUsersConnectionRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.JpaUsersConnectionRepository;

import pangeanembassy.security.domain.User;

/**
 * Custom UsersConnectionRepository which bridges between local authentications (with email address principals)
 * to social authentications (with numeric user id principals).  This class could extend SpringSocialSecurityJdbcUsersConnectionRepository
 * in order to use spring-social's jdbc connection factory with a fix for nulls returned from a ConnectionSignUp
 * implementation in event a local account cannot be created.  Or this class could extend JpaUserConnectionRepository
 * to use spring-social-jpa implementation.
 * 
 * @author Michael Lavelle
 */
public class CustomUsersConnectionRepository extends JpaUsersConnectionRepository  
implements UsersConnectionRepository
{
	@Inject
	public CustomUsersConnectionRepository(JpaTemplate jpaTemplate,
			ConnectionFactoryLocator connectionFactoryLocator,
			TextEncryptor textEncryptor) {
		super(jpaTemplate, connectionFactoryLocator, textEncryptor);
	}
	
	private String getLocalUserId(String springSocialUserId)
	{
		User User = new User();
		User =  User.findUser(Long.parseLong(springSocialUserId));
		return User == null ? null : User.getEmailAddress();
		
	}
	
	private Long getSpringSocialUserId(String localUserId)
	{
		  try {
		    	TypedQuery<User> query= User.findUsersByEmailAddress(localUserId);
		    	
		        User targetUser = (User) query.getSingleResult();
		        return targetUser == null ? null : targetUser.getId();
				
		  }
		  catch (NonUniqueResultException e)
		  {
			  throw new BadCredentialsException(
	          "Non-unique user, contact administrator");
	      }
		

	}
	

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		List<String> userIds = new ArrayList<String>();
		for (String springSocialUserId : super.findUserIdsWithConnection(connection))
		{
			String localUserId = getLocalUserId(springSocialUserId);
			if (localUserId != null)
			{
				userIds.add(springSocialUserId);
			}
		}
		return userIds;
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId,
			Set<String> providerUserIds) {
		Set<String> userIds = new HashSet<String>();
		for (String springSocialUserId : super.findUserIdsConnectedTo(providerId,providerUserIds))
		{
			String localUserId = getLocalUserId(springSocialUserId);

			if (localUserId != null)
			{
				userIds.add(springSocialUserId);
			}
		}
		return userIds;
	}

	public ConnectionRepository createLocalConnectionRepository(String localUserId) {
		Long springSocialUserId = getSpringSocialUserId(localUserId);
		return super.createConnectionRepository(springSocialUserId == null ? null : springSocialUserId.toString());
	}
	
	

}
