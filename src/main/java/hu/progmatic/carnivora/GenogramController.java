package hu.progmatic.carnivora;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class GenogramController {

    // AUTOWIRED-ek  --------------------------------------------------------------------------------

    @Autowired
    private KladForGsonService kladForGsonService;

    // REQUEST MAPPINGEK --------------------------------------------------------------------------------

    @RequestMapping("/genogramadmin")
    public String genogramAdmin() {
        return "genogram_admin";
    }

    @RequestMapping("/genogram")
    public String genogramNoAdmin() {
        return "genogram";
    }

    /*
    @GetMapping("/faj_adatszerk/{id}")
    public String szerkeszt(@PathVariable Integer id, Model model) {
        FajDto formFajDto = fajService.getFajDtoByFajId(id);
        model.addAttribute("formFajDto", formFajDto);
        return "faj_adatszerk";
    }
     */

    // POST MAPPINGEK --------------------------------------------------------------------------------

    @PostMapping("/save_json_to_database")
    public String saveToDatabase(
            @ModelAttribute("jsonForGenogram") @Valid JsonForGenogramDto json,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            kladForGsonService.updateRepository(json);
            model.addAttribute("jsonForGenogram", getJsonForGenogram());
            return "genogram_admin";
        }
        return "genogram_admin";
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("jsonForGenogram")
    JsonForGenogramDto getJsonForGenogram() {
        return kladForGsonService.getJsonForGenogram();
    }

}