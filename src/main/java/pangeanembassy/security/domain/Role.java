package pangeanembassy.security.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity
public class Role {

    @NotNull
    @Column(unique = true)
    @Size(min = 1)
    private String roleName;

    @NotNull
    @Size(max = 200)
    private String roleDescription;
}
