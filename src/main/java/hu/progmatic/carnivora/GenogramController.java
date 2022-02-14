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

    // GET MAPPINGEK --------------------------------------------------------------------------------

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
            Model model) {
        String submittedString = (String) model.getAttribute("jsonForGenogram");
        model.addAttribute("jsonForGenogram","a submit gomb eléri a save_json_to_database PostMappinget");
        return "genogram_admin";
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("jsonForGenogram")
    String getJsonForGenogram() {
        return kladForGsonService.getJsonForGenogram();
    }
}