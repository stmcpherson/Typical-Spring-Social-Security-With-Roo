/**
 * 
 */
package pangeanembassy.security.web;

/**
 * @author rohit
 * 
 */
public class ChangePasswordForm {

	private String oldPassword;
	private String newPassword;
	private String newPasswordAgain;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordAgain() {
		return newPasswordAgain;
	}

	public void setNewPasswordAgain(String newPasswordAgain) {
		this.newPasswordAgain = newPasswordAgain;
	}

}
