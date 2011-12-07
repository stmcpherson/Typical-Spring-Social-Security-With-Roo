/**
 * 
 */
package pangeanembassy.security.web;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pangeanembassy.security.domain.User;

/**
 * @author rohit
 * 
 */
@Service("changePasswordValidator")
public class ChangePasswordValidator implements Validator {

	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class.equals(clazz);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object target, Errors errors) {
		ChangePasswordForm form = (ChangePasswordForm) target;

		try {
			if (SecurityContextHolder.getContext().getAuthentication()
					.isAuthenticated()) {
				UserDetails userDetails = (UserDetails) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				Query query = User
						.findUsersByEmailAddress(userDetails.getUsername());
				if(null!=query){
					User person = (User) query.getSingleResult();
					String storedPassword = person.getPassword();
					String currentPassword = form.getOldPassword();
					if (!messageDigestPasswordEncoder.isPasswordValid(storedPassword, currentPassword, null)) {
						errors.rejectValue("oldPassword",
								"changepassword.invalidpassword");
					}
					String newPassword = form.getNewPassword();
					String newPasswordAgain = form.getNewPasswordAgain();
					if (!newPassword.equals(newPasswordAgain)) {
						errors.reject("changepassword.passwordsnomatch");
					}
				}
			}
		} catch (EntityNotFoundException e) {
			errors.rejectValue("emailAddress",
					"changepassword.invalidemailaddress");
		} catch (NonUniqueResultException e) {
			errors.rejectValue("emailAddress",
					"changepassword.duplicateemailaddress");
		}
	}

}
