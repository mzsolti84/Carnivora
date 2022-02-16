package hu.progmatic.carnivora;

import hu.progmatic.felhasznalo.FelhasznaloService;
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
        if (felhasznaloService.getFelhasznaloNev() != null)
        return felhasznaloService.getFelhasznaloNev();
        else return "";
    }

}