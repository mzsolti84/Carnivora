package hu.progmatic.carnivoraProject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class KozosOsController {

    @Autowired
    private CarnivoraService carnivoraService;

    @GetMapping("/carnivoraProject/kozosos")
    public String allFaj(Model model) {
        model.addAttribute("allSpecies", allSpecies());
        return "/carnivoraProject/kozosos";
    }

    /*@PostMapping("/carnivoraProject/kozosos/a")
    public String kereses(@ModelAttribute("kozosOs") @Valid KozosOs kozosOs, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            Integer id1 = kozosOs.species1.id;
            Integer id2 = kozosOs.species2.id;

        }
        return "/carnivoraProject/kozosos";
    }*/

    @GetMapping("/carnivoraProject/kozosos/a")
    public String kereses(@ModelAttribute("kozosOs") KozosOs kozosOs, Model model) {
        Integer id1 = kozosOs.species1.id;
        Integer id2 = kozosOs.species2.id;
        model.addAttribute("formBevitelUtan", true);
        model.addAttribute("megtalaltOs", carnivoraService.getById(id1));
        return "/carnivoraProject/kozosos";
    }

    @ModelAttribute("formBevitelUtan")
    public Boolean bevitelUtan(){
        return false;
    }

    @ModelAttribute("kozosOs")
    public KozosOs kozosOs() {
        return new KozosOs();
    }

    @ModelAttribute("allSpecies")
    List<FajRecord> allSpecies() {
        return carnivoraService.findAll();
    }

    @ModelAttribute("formSpecies")
    public FajRecord formItem() {
        return carnivoraService.empty();
    }
}
