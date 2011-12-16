/**
 * 
 */
package pangeanembassy.security.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaResponse;

/**
 * @author rohit
 * 
 */
public class SocialUserRegistrationForm {

	
	@NotNull
	@Size(min = 1)
	private String emailAddress;
	
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


}
