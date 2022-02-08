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

    // GET MAPPINGEK --------------------------------------------------------------------------------

    @RequestMapping ("/")
    public String belepes() {
        return "kezdolap";
    }

    @GetMapping("/carnivora/{id}")
    public String szerkeszt(@PathVariable Integer id, Model model) {
        Faj formFaj = carnivoraService.getById(id);
        model.addAttribute("formFaj", formFaj);
        return "carnivora";
    }

    @GetMapping("/carnivora/{id}/adatlap")
    public String adatlapKiir(@PathVariable Integer id, Model model) {
        Faj formFaj = carnivoraService.getById(id);
        model.addAttribute("formFaj", formFaj);
        return "carnivoraAdatlap";
    }

    @GetMapping("/carnivora/{id}/TalalatiKartyak")
    public String kartyaKiIr(@PathVariable Integer id, Model model) {
        Faj formFaj = carnivoraService.getById(id);
        model.addAttribute(formFaj);
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
            @ModelAttribute("formFaj") @Valid Faj formFaj,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            carnivoraService.save(formFaj);
            model.addAttribute("allSpecies", carnivoraService.findAll());
            model.addAttribute("formFaj", new Faj());
        }
        return "carnivora";
    }

    @PostMapping("/carnivora/")
    public String create(
            @ModelAttribute("formFaj") @Valid Faj formFaj,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            carnivoraService.create(formFaj);
            model.addAttribute("allSpecies", carnivoraService.findAll());
            model.addAttribute("formFaj", new Faj());
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
    List<Faj> allSpecies() {
        return carnivoraService.findAll();
    }

    @ModelAttribute("allKlad")
    List<SzuloKladDto> allKlad() {
        return kladService.findAllParentKlad();
    }

    @ModelAttribute("formFaj")
    public Faj formSpecies() {
        return new Faj();
    }

}