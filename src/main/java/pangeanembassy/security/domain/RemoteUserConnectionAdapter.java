package pangeanembassy.security.domain;

import org.springframework.social.connect.jpa.RemoteUser;

/**
 * Adapts a Roo UserConnection to a RemoteUser as required for spring-social-jpa
 * module, so that we can persist UserConnections using JpaConnectionRepository
 * 
 * @author Michael Lavelle
 */
public class RemoteUserConnectionAdapter implements RemoteUser {

	private UserConnection UserConnection;

	public RemoteUserConnectionAdapter(UserConnection UserConnection) {
		this.UserConnection = UserConnection;
	}

	public UserConnection getUserConnection() {
		return UserConnection;
	}

	@Override
	public String getAccessToken() {
		return UserConnection.getAccessToken();
	}

	@Override
	public String getDisplayName() {
		return UserConnection.getDisplayName();
	}

	@Override
	public Long getExpireTime() {
		return UserConnection.getExpireTime();
	}

	@Override
	public String getImageUrl() {
		return UserConnection.getImageUrl();
	}

	@Override
	public String getProfileUrl() {
		return UserConnection.getProfileUrl();
	}

	@Override
	public String getProviderId() {
		return UserConnection.getProviderId();
	}

	@Override
	public String getProviderUserId() {
		return UserConnection.getProviderUserId();
	}

	@Override
	public int getRank() {
		return UserConnection.getRank();
	}

	@Override
	public String getRefreshToken() {
		return UserConnection.getRefreshToken();
	}

	@Override
	public String getSecret() {
		return UserConnection.getSecret();
	}

	@Override
	public String getUserId() {
		return UserConnection.getUserId();
	}

	@Override
	public void setAccessToken(String accessToken) {
		UserConnection.setAccessToken(accessToken);
	}

	@Override
	public void setDisplayName(String displayName) {
		UserConnection.setDisplayName(displayName);
	}

	@Override
	public void setExpireTime(Long expireTime) {
		UserConnection.setExpireTime(expireTime);

	}

	@Override
	public void setImageUrl(String imageUrl) {
		UserConnection.setImageUrl(imageUrl);
	}

	@Override
	public void setProfileUrl(String profileUrl) {
		UserConnection.setProfileUrl(profileUrl);
	}

	@Override
	public void setProviderId(String providerId) {
		UserConnection.setProviderId(providerId);
	}

	@Override
	public void setProviderUserId(String providerUserId) {
		UserConnection.setProviderUserId(providerUserId);
	}

	@Override
	public void setRank(int rank) {
		UserConnection.setRank(rank);
	}

	@Override
	public void setRefreshToken(String refreshToken) {
		UserConnection.setRefreshToken(refreshToken);
	}

	@Override
	public void setSecret(String secret) {
		UserConnection.setSecret(secret);
	}

	@Override
	public void setUserId(String userId) {
		UserConnection.setUserId(userId);
	}

}
