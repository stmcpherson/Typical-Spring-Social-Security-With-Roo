package pangeanembassy.security.web;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

import pangeanembassy.security.domain.User;

public class CustomUsersConnectionRepository extends JdbcUsersConnectionRepository  
implements UsersConnectionRepository
{
	@Inject
	public CustomUsersConnectionRepository(DataSource dataSource,
			ConnectionFactoryLocator connectionFactoryLocator,
			TextEncryptor textEncryptor) {
		super(dataSource, connectionFactoryLocator, textEncryptor);
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
