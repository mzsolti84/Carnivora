package hu.progmatic.carnivora;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    /*
    @PostMapping("/faj_adatszerk/{id}")
    public String update(
            @PathVariable Integer id,
            @ModelAttribute("formFajDto") @Valid FajDto formFajDto,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            //FajDto fajDto = fajService.getById(id);
            fajService.save(formFajDto);
            model.addAttribute("allFajDto", allFajDto());
            model.addAttribute("formFajDto", formFajDto());
        }
        return "faj_adatlista";
    }
     */

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("jsonForGenogram")
    String getJsonForGenogram() {
        return kladForGsonService.getJsonForGenogram();
    }
}