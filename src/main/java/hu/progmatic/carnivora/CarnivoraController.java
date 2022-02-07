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
        return "carnivora/carnivora";
    }

    private String adatlap() {
        return "carnivora/carnivoraAdatlap";
    }

    private String kartya() {
        return "carnivora/talalatiKartyak";
    }

    // GET MAPPINGEK --------------------------------------------------------------------------------

    @RequestMapping("/")
    public String species() {
        return "carnivora/carnivora";
    }

    @GetMapping("/carnivora/carnivora/{id}")
    public String szerkeszt(@PathVariable Integer id, Model model) {
        Species formSpecies = carnivoraService.getById(id);
        model.addAttribute("formSpecies", formSpecies);
        return "carnivora/carnivora";
    }

    @GetMapping("/carnivora/carnivora/{id}/adatlap")
    public String adatlapKiir(@PathVariable Integer id, Model model) {
        Species formSpecies = carnivoraService.getById(id);
        model.addAttribute("formSpecies", formSpecies);
        return "carnivora/carnivoraAdatlap";
    }

    @GetMapping("/carnivora/carnivora/{id}/TalalatiKartyak")
    public String kartyaKiIr(@PathVariable Integer id, Model model) {
        Species formSpecies = carnivoraService.getById(id);
        model.addAttribute(formSpecies);
        return "carnivora/talalatiKartyak";
    }

    @RequestMapping("/carnivora/kozosos")
    public String kozosOS() {
        return "carnivora/kozosos";
    }

    @RequestMapping("/carnivora/kezdolap")
    public String kezdolap() {
        return "carnivora/kezdolap";
    }

    // POST MAPPINGEK --------------------------------------------------------------------------------

    @PostMapping("/carnivora/carnivora/{id}")
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
        return "carnivora/carnivora";
    }

    @PostMapping("/carnivora/carnivora/")
    public String create(
            @ModelAttribute("formSpecies") @Valid Species formSpecies,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            carnivoraService.create(formSpecies);
            model.addAttribute("allSpecies", carnivoraService.findAll());
            model.addAttribute("formSpecies", new Faj());
        }
        return "carnivora/carnivora";
    }

    @PostMapping("/carnivora/carnivora/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        carnivoraService.deleteById(id);
        model.addAttribute("allSpecies", carnivoraService.findAll());
        return "carnivora/carnivora";
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
    public Faj formSpecies() {
        return new Faj();
    }

    // MODEL MÓDOSÍTÓK -------------------------------------------------------------------------------

    private void refreshAllSpecies(Model model) {
        model.addAttribute("allSpecies", allSpecies());
    }

    private void clearFormItem(Model model) {
        model.addAttribute("formSpecies", formSpecies());
    }

}