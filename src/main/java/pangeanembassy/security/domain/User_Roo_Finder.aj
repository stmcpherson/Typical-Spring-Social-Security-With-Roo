// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package pangeanembassy.security.domain;

import java.lang.String;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import pangeanembassy.security.domain.User;

privileged aspect User_Roo_Finder {
    
    public static TypedQuery<User> User.findUsersByActivationKeyAndEmailAddress(String activationKey, String emailAddress) {
        if (activationKey == null || activationKey.length() == 0) throw new IllegalArgumentException("The activationKey argument is required");
        if (emailAddress == null || emailAddress.length() == 0) throw new IllegalArgumentException("The emailAddress argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.activationKey = :activationKey AND o.emailAddress = :emailAddress", User.class);
        q.setParameter("activationKey", activationKey);
        q.setParameter("emailAddress", emailAddress);
        return q;
    }
    
    public static TypedQuery<User> User.findUsersByEmailAddress(String emailAddress) {
        if (emailAddress == null || emailAddress.length() == 0) throw new IllegalArgumentException("The emailAddress argument is required");
        EntityManager em = User.entityManager();
        TypedQuery<User> q = em.createQuery("SELECT o FROM User AS o WHERE o.emailAddress = :emailAddress", User.class);
        q.setParameter("emailAddress", emailAddress);
        return q;
    }
    
}
