// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package pangeanembassy.security.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Set;
import pangeanembassy.security.domain.User;
import pangeanembassy.security.domain.UserRole;

privileged aspect UserConnection_Roo_Finder {
    
    public static TypedQuery<UserConnection> UserConnection.findUserConnectionsByUserId(String userId) {
        if (userId == null) throw new IllegalArgumentException("The userId argument is required");
        EntityManager em = UserConnection.entityManager();
        TypedQuery<UserConnection> q = em.createQuery("SELECT o FROM UserConnection AS o WHERE o.userId = :userId order by o.rank", UserConnection.class);
        q.setParameter("userId", userId);
        return q;
    }
    
    public static TypedQuery<UserConnection> UserConnection.findUserConnectionsByUserIdAndProviderId(String userId,String providerId) {
        if (userId == null) throw new IllegalArgumentException("The userId argument is required");
        if (providerId == null) throw new IllegalArgumentException("The providerId argument is required");
       
        EntityManager em = UserConnection.entityManager();
        TypedQuery<UserConnection> q = em.createQuery("SELECT o FROM UserConnection AS o WHERE o.userId = :userId and o.providerId = :providerId order by o.rank", UserConnection.class);
        q.setParameter("userId", userId);
        q.setParameter("providerId", providerId);
        
        return q;
    }
    
     public static TypedQuery<Integer> UserConnection.findMaxRankByUserIdAndProviderId(String userId,String providerId) {
        if (userId == null) throw new IllegalArgumentException("The userId argument is required");
        if (providerId == null) throw new IllegalArgumentException("The providerId argument is required");
       
        EntityManager em = UserConnection.entityManager();
        TypedQuery<Integer> q = em.createQuery("SELECT max(o.rank) FROM UserConnection AS o WHERE o.userId = :userId and o.providerId = :providerId", Integer.class);
        q.setParameter("userId", userId);
        q.setParameter("providerId", providerId);
        
        return q;
    }
    
    public static TypedQuery<UserConnection> UserConnection.findUserConnectionsByUserIdAndProviderIdAndRank(String userId,String providerId,int rank) {
        if (userId == null) throw new IllegalArgumentException("The userId argument is required");
        if (providerId == null) throw new IllegalArgumentException("The providerId argument is required");
       
        EntityManager em = UserConnection.entityManager();
        TypedQuery<UserConnection> q = em.createQuery("SELECT o FROM UserConnection AS o WHERE o.userId = :userId and o.providerId = :providerId and o.rank = :rank order by o.rank", UserConnection.class);
        q.setParameter("userId", userId);
        q.setParameter("providerId", providerId);
        q.setParameter("rank", rank);
        
        return q;
    }
    

    
     public static TypedQuery<UserConnection> UserConnection.findUserConnectionsByProviderIdAndProviderUserId(String providerId,String providerUserId) {
        if (providerId == null) throw new IllegalArgumentException("The providerId argument is required");
        if (providerUserId == null) throw new IllegalArgumentException("The providerUserId argument is required");
       
        EntityManager em = UserConnection.entityManager();
        TypedQuery<UserConnection> q = em.createQuery("SELECT o FROM UserConnection AS o WHERE o.providerId = :providerId and o.providerUserId = :providerUserId order by o.rank", UserConnection.class);
        q.setParameter("providerId", providerId);
        q.setParameter("providerUserId", providerUserId);
        
        return q;
    }
    
    
    public static TypedQuery<String> UserConnection.findUserIdsByProviderIdAndProviderUserIds(String providerId,Set<String> providerUserIds) {
        if (providerId == null) throw new IllegalArgumentException("The providerId argument is required");
        if (providerUserIds == null) throw new IllegalArgumentException("The providerUserIds argument is required");
       
        EntityManager em = UserConnection.entityManager();
        TypedQuery<String> q = em.createQuery("SELECT distinct(o.userId) FROM UserConnection AS o WHERE o.providerId = :providerId and o.providerUserId in :providerUserIds order by o.rank", String.class);
        q.setParameter("providerId", providerId);
        q.setParameter("providerUserIds", providerUserIds);
        
        return q;
    }
    
   

     public static UserConnection UserConnection.findUserConnectionByUserIdAndProviderIdAndProviderUserId(String userId,String providerId,String providerUserId) {
        if (userId == null) throw new IllegalArgumentException("The userId argument is required");
        if (providerId == null) throw new IllegalArgumentException("The providerId argument is required");
        if (providerUserId == null) throw new IllegalArgumentException("The providerUserId argument is required");
       
        EntityManager em = UserConnection.entityManager();
        TypedQuery<UserConnection> q = em.createQuery("SELECT o FROM UserConnection AS o WHERE o.userId = :userId and o.providerId = :providerId and o.providerUserId = :providerUserId", UserConnection.class);
        q.setParameter("userId", userId);
        q.setParameter("providerId", providerId);
        q.setParameter("providerUserId", providerUserId);
        // Spring Social ensures that only one entry exists for a given userId,ProviderId and providerUserId
        // So we return the single result here - we may want to handle this differently
        return q.getSingleResult();
    }
    
    
}
