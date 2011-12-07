package pangeanembassy.security.web;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pangeanembassy.security.domain.Role;

@RooWebScaffold(path = "roles", formBackingObject = Role.class)
@RequestMapping("/roles")
@Controller
public class RoleController {
}
