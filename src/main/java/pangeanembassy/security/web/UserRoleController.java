package pangeanembassy.security.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pangeanembassy.security.domain.UserRole;

@RooWebScaffold(path = "userroles", formBackingObject = UserRole.class)
@RequestMapping("/userroles")
@Controller
public class UserRoleController {
}
