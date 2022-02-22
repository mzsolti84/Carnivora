package hu.progmatic.carnivora;


import hu.progmatic.felhasznalo.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
public class GenogramController {

    // AUTOWIRED-ek  --------------------------------------------------------------------------------

    @Autowired
    private GsonService gsonService;

    // REQUEST MAPPINGEK --------------------------------------------------------------------------------

    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    @RequestMapping("/genogramadmin")
    public String genogramAdmin() {
        return "genogram_admin";
    }

    @RequestMapping("/genogram")
    public String genogramNoAdmin() {
        return "genogram";
    }

    // POST MAPPINGEK --------------------------------------------------------------------------------

    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    @PostMapping("/save_json_to_database")
    public String saveToDatabase(
            @ModelAttribute("jsonForGenogram") @Valid JsonForGenogramDto json,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            gsonService.updateKladRepository(json);
            model.addAttribute("jsonForGenogram", getJsonForGenogram());
            return "genogram_admin";
        }
        return "genogram_admin";
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("jsonForGenogram")
    JsonForGenogramDto getJsonForGenogram() {
        return gsonService.getJsonForGenogram();
    }

}