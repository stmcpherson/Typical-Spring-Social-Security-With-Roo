package pangeanembassy.security.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.social.connect.jpa.RemoteUser;

import pangeanembassy.security.domain.User;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import pangeanembassy.security.domain.Role;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findUserConnectionsByUserId","findUserConnectionsByUserIdAndProviderId",
		"findUserConnectionsByUserIdAndProviderIdAndRank","findUserConnectionsByProviderIdAndProviderUserId",
		"findUserConnectionByUserIdAndProviderIdAndProviderUserId","findMaxRankByUserIdAndProviderId","findUserIdsByProviderIdAndProviderUserIds"})
public class UserConnection{

	private String accessToken;
	private String displayName;
	private Long expireTime;
	private String imageUrl;
	private String profileUrl;
	private String providerId;
	private String providerUserId;
	private int rank;
	private String refreshToken;
	private String secret;
	private String userId;
}
