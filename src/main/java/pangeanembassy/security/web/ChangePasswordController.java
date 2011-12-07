package pangeanembassy.security.web;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pangeanembassy.security.domain.User;

@RequestMapping("/changepassword/**")
@Controller
public class ChangePasswordController {
	@Autowired
	private ChangePasswordValidator validator;

	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

	@ModelAttribute("changePasswordForm")
	public ChangePasswordForm formBackingObject() {
		return new ChangePasswordForm();
	}

	@RequestMapping(value = "/changepassword/index")
	public String index() {
		if (SecurityContextHolder.getContext().getAuthentication()
				.isAuthenticated()) {
			return "changepassword/index";
		} else {
			return "login";
		}
	}

	@RequestMapping(value = "/changepassword/update", method = RequestMethod.POST)
	public String update(
			@ModelAttribute("changePasswordForm") ChangePasswordForm form,
			BindingResult result) {
		validator.validate(form, result);
		if (result.hasErrors()) {
			return "changepassword/index"; // back to form
		} else {
			if (SecurityContextHolder.getContext().getAuthentication()
					.isAuthenticated()) {
				UserDetails userDetails = (UserDetails) SecurityContextHolder
						.getContext().getAuthentication().getPrincipal();
				String newPassword = form.getNewPassword();
				Query query = User
						.findUsersByEmailAddress(userDetails.getUsername());
				User person = (User) query.getSingleResult();
				person.setPassword(messageDigestPasswordEncoder.encodePassword(newPassword, null));
				person.merge();
				return "changepassword/thanks";
			} else {
				return "login";
			}
		}
	}

	@RequestMapping(value = "/changepassword/thanks")
	public String thanks() {
		return "changepassword/thanks";
	}

}
