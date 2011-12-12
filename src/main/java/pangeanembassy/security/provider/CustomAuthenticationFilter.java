
package pangeanembassy.security.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.socialsignin.springframework.social.security.signin.AuthenticatedUserIdHolder;
import org.socialsignin.springframework.social.security.signin.SpringSocialSecuritySignInDetails;
import org.socialsignin.springframework.social.security.signin.SpringSocialSecuritySignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Service;

import pangeanembassy.security.domain.User;

@Service
public class CustomAuthenticationFilter extends
		AbstractAuthenticationProcessingFilter {

	@Autowired
	private DatabaseAuthenticationProvider databaseAuthenticationProvider;
	
	private boolean removeSignInDetailsFromSessionOnSuccessfulAuthentication = true;
	
	private boolean allowRepeatedAuthenticationAttempts = false;
	
	public void setAllowRepeatedAuthenticationAttempts(
			boolean allowRepeatedAuthenticationAttempts) {
		this.allowRepeatedAuthenticationAttempts = allowRepeatedAuthenticationAttempts;
	}
	
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager)
	{
		super.setAuthenticationManager(authenticationManager);
	}
 
	
	protected CustomAuthenticationFilter() {
		super("/authenticate");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException,
			IOException, ServletException {

		SpringSocialSecuritySignInDetails signInDetails = (SpringSocialSecuritySignInDetails)request.getSession().getAttribute(SpringSocialSecuritySignInService.SIGN_IN_DETAILS_SESSION_ATTRIBUTE_NAME);
		String alreadyAuthenticatedUserId = AuthenticatedUserIdHolder.getAuthenticatedUserId();
		User User = new User();
		User = signInDetails == null ? null : User.findUser(Long.parseLong(signInDetails.getUserId()));
		if (User != null)
		{		
			UsernamePasswordAuthenticationToken token = new SpringSocialAuthenticationToken(User.getEmailAddress());
			
			Authentication authentication = databaseAuthenticationProvider.authenticate(token);
			if (authentication != null && removeSignInDetailsFromSessionOnSuccessfulAuthentication)
			{
				request.getSession().removeAttribute(SpringSocialSecuritySignInService.SIGN_IN_DETAILS_SESSION_ATTRIBUTE_NAME);
			}
			return authentication;
		}
		else if (allowRepeatedAuthenticationAttempts && alreadyAuthenticatedUserId != null)
		{
			return SecurityContextHolder.getContext().getAuthentication();
		}
		else
		{
			throw new InsufficientAuthenticationException("SpringSocialSecurity sign in details not found in session");
		}
		
	}

}
