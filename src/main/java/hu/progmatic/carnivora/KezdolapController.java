package hu.progmatic.carnivora;

import hu.progmatic.felhasznalo.FelhasznaloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class KezdolapController {

    @Autowired
    FelhasznaloService felhasznaloService;

    @ModelAttribute("loggedUserName")
    public String loggedUserName() {
        return felhasznaloService.getFelhasznaloNev() + "bla";
    }

}
