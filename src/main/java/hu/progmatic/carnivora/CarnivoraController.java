package hu.progmatic.carnivora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CarnivoraController {

    // AUTOWIRED-ek  --------------------------------------------------------------------------------

    @Autowired
    private CarnivoraService carnivoraService;

    @Autowired
    private KladService kladService;

    // RETURN to .HTML-ek ---------------------------------------------------------------------------

    private String carnivora() {
        return "carnivora";
    }

    private String adatlap() {
        return "carnivoraAdatlap";
    }

    private String kartya() {
        return "talalatiKartyak";
    }

    // GET MAPPINGEK --------------------------------------------------------------------------------

    @RequestMapping ("/")
    public String belepes() {
        return "kezdolap";
    }

    @GetMapping("/carnivora/{id}")
    public String szerkeszt(@PathVariable Integer id, Model model) {
        Species formSpecies = carnivoraService.getById(id);
        model.addAttribute("formSpecies", formSpecies);
        return "carnivora";
    }

    @GetMapping("/carnivora/{id}/adatlap")
    public String adatlapKiir(@PathVariable Integer id, Model model) {
        Species formSpecies = carnivoraService.getById(id);
        model.addAttribute("formSpecies", formSpecies);
        return "carnivoraAdatlap";
    }

    @GetMapping("/carnivora/{id}/TalalatiKartyak")
    public String kartyaKiIr(@PathVariable Integer id, Model model) {
        Species formSpecies = carnivoraService.getById(id);
        model.addAttribute(formSpecies);
        return "talalatiKartyak";
    }

    @RequestMapping("/kozosos")
    public String kozosOS() {
        return "kozosos";
    }

    @RequestMapping("/carnivora")
    public String fajAdatbazis() {
        return "carnivora";
    }

    // POST MAPPINGEK --------------------------------------------------------------------------------

    @PostMapping("/carnivora/{id}")
    public String save(
            @PathVariable Integer id,
            @ModelAttribute("formSpecies") @Valid Species formSpecies,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            carnivoraService.save(formSpecies);
            model.addAttribute("allSpecies", carnivoraService.findAll());
            model.addAttribute("formSpecies", new Species());
        }
        return "carnivora";
    }

    @PostMapping("/carnivora/")
    public String create(
            @ModelAttribute("formSpecies") @Valid Species formSpecies,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            carnivoraService.create(formSpecies);
            model.addAttribute("allSpecies", carnivoraService.findAll());
            model.addAttribute("formSpecies", new Species());
        }
        return "carnivora";
    }

    @PostMapping("/carnivora/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        carnivoraService.deleteById(id);
        model.addAttribute("allSpecies", carnivoraService.findAll());
        return "carnivora";
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("allSpecies")
    List<Species> allSpecies() {
        return carnivoraService.findAll();
    }

    @ModelAttribute("allKlad")
    List<ParentKladDto> allKlad() {
        return kladService.findAllParentKlad();
    }

    @ModelAttribute("formSpecies")
    public Species formSpecies() {
        return new Species();
    }

    // MODEL MÓDOSÍTÓK -------------------------------------------------------------------------------

    private void refreshAllSpecies(Model model) {
        model.addAttribute("allSpecies", allSpecies());
    }

    private void clearFormItem(Model model) {
        model.addAttribute("formSpecies", formSpecies());
    }

}