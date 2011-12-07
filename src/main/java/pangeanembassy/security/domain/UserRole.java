package pangeanembassy.security.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import pangeanembassy.security.domain.User;
import javax.validation.constraints.NotNull;
import javax.persistence.ManyToOne;
import pangeanembassy.security.domain.Role;

@RooJavaBean
@RooToString
@RooEntity(finders = { "findUserRolesByUserEntry" })
public class UserRole {

    @NotNull
    @ManyToOne
    private User userEntry;

    @NotNull
    @ManyToOne
    private Role roleEntry;
}
