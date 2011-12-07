/**
 * 
 */
package pangeanembassy.security.provider;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import pangeanembassy.security.domain.Role;
import pangeanembassy.security.domain.User;
import pangeanembassy.security.domain.UserRole;

/**
 * @author rohit
 * 
 */
@Service("databaseAuthenticationProvider")
public class DatabaseAuthenticationProvider extends
		AbstractUserDetailsAuthenticationProvider {
	private final Logger logger = Logger.getLogger(getClass());

	private String adminUser;
	private String adminPassword;

	
	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}

	
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.dao.
	 * AbstractUserDetailsAuthenticationProvider
	 * #additionalAuthenticationChecks(org
	 * .springframework.security.core.userdetails.UserDetails,
	 * org.springframework
	 * .security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails arg0,
			UsernamePasswordAuthenticationToken arg1)
			throws AuthenticationException {
		return;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.dao.
	 * AbstractUserDetailsAuthenticationProvider#retrieveUser(java.lang.String,
	 * org
	 * .springframework.security.authentication.UsernamePasswordAuthenticationToken
	 * )
	 */
	@Override
	protected UserDetails retrieveUser(String username,
		      UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		logger.log(Priority.DEBUG, "Inside retrieveUser");
		String password = (String) authentication.getCredentials();
	    if (! StringUtils.hasText(password)) {
	      throw new BadCredentialsException("Please enter password");
	    }
	    String encryptedPassword = messageDigestPasswordEncoder.encodePassword(password, null); 
	    UserDetails user = null;
	    String expectedPassword = null;
	    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	    if (adminUser.equals(username)) {
	      // pseudo-user admin (ie not configured via Person)
	      expectedPassword = adminPassword; 
	      // authenticate admin
	      if (! encryptedPassword.equals(expectedPassword)) {
	        throw new BadCredentialsException("Invalid password");
	      }
	      // authorize admin
	      authorities.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
	    } else {
	      try {
	    	TypedQuery<User> query= User.findUsersByEmailAddress(username);
	    	
	        User targetUser = (User) query.getSingleResult();
	        // authenticate the person
	        expectedPassword = targetUser.getPassword();
	        if (! StringUtils.hasText(expectedPassword)) {
	          throw new BadCredentialsException("No password for " + username + 
	            " set in database, contact administrator");
	        }
	        if (! encryptedPassword.equals(expectedPassword)) {
	          throw new BadCredentialsException("Invalid Password");
	        }
	        
	        TypedQuery<UserRole> roleQuery=UserRole.findUserRolesByUserEntry(targetUser);
	        List<UserRole> userRoles = roleQuery.getResultList();
	        for(UserRole userRole:userRoles){
	        	authorities.add(new GrantedAuthorityImpl(userRole.getRoleEntry().getRoleName()));
	        }
	      } catch (EmptyResultDataAccessException e) {
		        throw new BadCredentialsException("Invalid user");
	      } catch (EntityNotFoundException e) {
	        throw new BadCredentialsException("Invalid user");
	      } catch (NonUniqueResultException e) {
	        throw new BadCredentialsException(
	          "Non-unique user, contact administrator");
	      }
	    }
	    return new org.springframework.security.core.userdetails.User(
	      username,
	      password,
	      true, // enabled 
	      true, // account not expired
	      true, // credentials not expired 
	      true, // account not locked
	      authorities
	    );
	}
}
