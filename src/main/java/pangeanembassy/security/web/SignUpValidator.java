/**
 * 
 */
package pangeanembassy.security.web;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author rohit
 * 
 */
@Service("signupValidator")
public class SignUpValidator implements Validator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistrationForm.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		UserRegistrationForm form = (UserRegistrationForm) target;

		String newPassword = form.getPassword();
		String newPasswordAgain = form.getRepeatPassword();
		if (!newPassword.equals(newPasswordAgain)) {
			errors.reject("changepassword.passwordsnomatch");
		}
		
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LdfmL8SAAAAAHKPqUQV5SxrRX9Id6a8cQo-mgpE");

        String challenge = form.getRecaptcha_challenge_field();
        String uresponse = form.getRecaptcha_response_field();
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer("localhost", challenge, uresponse);
        if (!reCaptchaResponse.isValid()) {
        	errors.reject("recaptcha.mismatch");
        }


	}

}
