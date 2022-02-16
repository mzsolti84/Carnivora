package hu.progmatic.carnivora;

import hu.progmatic.felhasznalo.FelhasznaloService;
import hu.progmatic.felhasznalo.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KezdolapController {

    @Autowired
    FelhasznaloService felhasznaloService;

    @RequestMapping("/")
    public String belepes() {
        return "kezdolap";
    }

    @ModelAttribute("loggedUserId")
    public Long userId() {
        return felhasznaloService.getFelhasznaloId();
    }

    @ModelAttribute("loggedUserName")
    public String loggedUserName() {
        return felhasznaloService.getFelhasznaloNev();
    }

    @ModelAttribute("isAnonymusUser")
    public Boolean isAnonymusUser() {
        return felhasznaloService.isAnonymusUser();
    }

    @ModelAttribute("loggedHasUserWriteRole")
    public boolean loggedHasUserWriteRole() {
        if (!felhasznaloService.isAnonymusUser())
            return felhasznaloService.hasRole(UserType.Roles.USER_WRITE_ROLE);
        else return false;
    }
}
