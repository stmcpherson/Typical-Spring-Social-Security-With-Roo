package pangeanembassy.security.provider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.social.connect.jpa.JpaTemplate;
import org.springframework.social.connect.jpa.RemoteUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import pangeanembassy.security.domain.RemoteUserConnectionAdapter;
import pangeanembassy.security.domain.UserConnection;



/**
 * JpaTemplate implementation to enable use of spring-social-jpa to persist UserConnections.  This is
 * a prototype implementation which is functionaly complete but can be improved in terms of efficiency.
 * In particular, the methods getRank(),findUsersConnectedTo() and getAll(String userId,
 *			MultiValueMap<String, String> providerUsers) are naive implementations which
 * use existing finder methods to load lists of objects and apply operations to each of the
 * loaded objects.  These methods can be improved by writing more specialised finder/remove methods
 * on the entities themselves to remove need to load lists of objects but delegate work to the
 * DAO layer.
 * 
 * @author Michael Lavelle
 */
@Service
public class RooUserConnectionTemplate implements JpaTemplate {

	@Override
	public Set<String> findUsersConnectedTo(String providerId,
			Set<String> providerUserIds) {
		
		return new HashSet<String>(UserConnection.findUserIdsByProviderIdAndProviderUserIds(providerId,providerUserIds).getResultList());
	}

	@Override
	public List<RemoteUser> getPrimary(String userId, String providerId) {
		List<RemoteUser> remoteUsers = new ArrayList<RemoteUser>();
		for (UserConnection userConnection : UserConnection.findUserConnectionsByUserIdAndProviderIdAndRank(userId,providerId,1).getResultList())
		{
			remoteUsers.add(new RemoteUserConnectionAdapter(userConnection));
		}
		return remoteUsers;
	}

	@Override
	@Transactional
	public int getRank(String userId, String providerId) {
		
		Integer maxRank = UserConnection.findMaxRankByUserIdAndProviderId(userId,providerId).getSingleResult();
		return maxRank == null ? 1 : (maxRank.intValue() + 1);
	}

	@Override
	public List<RemoteUser> getAll(String userId,
			MultiValueMap<String, String> providerUsers) {
		List<RemoteUser> remoteUsers = new ArrayList<RemoteUser>();
		for (Map.Entry<String, List<String>> providerUsersEntry : providerUsers.entrySet())
		{
			String providerId = providerUsersEntry.getKey();
			for (String providerUserId : providerUsersEntry.getValue())
			{
				remoteUsers.add(get(userId,providerId,providerUserId));
			}
		}
		return remoteUsers;
	}

	@Override
	public List<RemoteUser> getAll(String userId) {
		List<RemoteUser> remoteUsers = new ArrayList<RemoteUser>();
	
		for (UserConnection userConnection : UserConnection.findUserConnectionsByUserId(userId).getResultList())
		{
			remoteUsers.add(new RemoteUserConnectionAdapter(userConnection));
		}
		return remoteUsers;
	}

	@Override
	public List<RemoteUser> getAll(String userId, String providerId) {
		List<RemoteUser> remoteUsers = new ArrayList<RemoteUser>();
		for (UserConnection userConnection : UserConnection.findUserConnectionsByUserIdAndProviderId(userId,providerId).getResultList())
		{
			remoteUsers.add(new RemoteUserConnectionAdapter(userConnection));
		}
		return remoteUsers;
	}

	@Override
	public RemoteUser get(String userId, String providerId,
			String providerUserId) {
		
		UserConnection userConnection = UserConnection.findUserConnectionByUserIdAndProviderIdAndProviderUserId(userId,providerId,providerUserId);
		if (userConnection == null) return null;
		return new RemoteUserConnectionAdapter(userConnection);
	}

	@Override
	public List<RemoteUser> get(String providerId, String providerUserId)
			throws IncorrectResultSizeDataAccessException {
		List<RemoteUser> remoteUsers = new ArrayList<RemoteUser>();
		for (UserConnection userConnection : UserConnection.findUserConnectionsByProviderIdAndProviderUserId(providerId,providerUserId).getResultList())
		{
			remoteUsers.add(new RemoteUserConnectionAdapter(userConnection));
		}
		return remoteUsers;	}

	@Override
    @Transactional
	public void remove(String userId, String providerId) {
		for (UserConnection userConnection : UserConnection.findUserConnectionsByUserIdAndProviderId(userId,providerId).getResultList())
		{
			userConnection.remove();
		}
	}

	@Override
	public void remove(String userId, String providerId, String providerUserId) {
		UserConnection userConnection = UserConnection.findUserConnectionByUserIdAndProviderIdAndProviderUserId(userId,providerId,providerUserId);
		if (userConnection != null) userConnection.remove();
	}

	@Override
	public RemoteUser createRemoteUser(String userId, String providerId,
			String providerUserId, int rank, String displayName,
			String profileUrl, String imageUrl, String accessToken,
			String secret, String refreshToken, Long expireTime) {
		UserConnection userConnection = new UserConnection();
		RemoteUserConnectionAdapter remoteUser = new RemoteUserConnectionAdapter(userConnection);

		remoteUser.setUserId(userId);
		remoteUser.setProviderId(providerId);
		remoteUser.setProviderUserId(providerUserId);
		remoteUser.setRank(rank);
		remoteUser.setDisplayName(displayName);
		remoteUser.setProfileUrl(profileUrl);
		remoteUser.setImageUrl(imageUrl);
		remoteUser.setAccessToken(accessToken);
		remoteUser.setSecret(secret);
		remoteUser.setRefreshToken(refreshToken);
		remoteUser.setExpireTime(expireTime);
		remoteUser.getUserConnection().persist();
		return remoteUser;
	}

	@Override
	public RemoteUser save(RemoteUser remoteUser) {
		((RemoteUserConnectionAdapter)remoteUser).getUserConnection().persist();
		return remoteUser;
	}

}
