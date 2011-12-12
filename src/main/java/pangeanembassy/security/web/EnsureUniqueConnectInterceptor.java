package pangeanembassy.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public class EnsureUniqueConnectInterceptor<S> implements ConnectInterceptor<S> {

	@Autowired
	private CustomUsersConnectionRepository usersConnectionRepository;
	
	
	@Override
	public void preConnect(ConnectionFactory<S> connectionFactory,
			MultiValueMap<String, String> parameters, WebRequest request) 
	{
		
		
	}

	@Override
	public void postConnect(Connection<S> connection, WebRequest request) {
		
		boolean connectionAlreadyAssociatedWithAnotherUser = 
			usersConnectionRepository.findUserIdsWithConnection(connection).size() >1;
			if (connectionAlreadyAssociatedWithAnotherUser)
			{
				Authentication localAuthentication = SecurityContextHolder.getContext().getAuthentication();
				String localUserId = localAuthentication == null ? null : localAuthentication.getName();
				usersConnectionRepository.createLocalConnectionRepository(localUserId).removeConnection(connection.getKey());
				throw new NonUniqueConnectionException("The connection is already associated with a different account");
			}
		
	}

}
