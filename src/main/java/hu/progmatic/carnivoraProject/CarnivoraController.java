package hu.progmatic.carnivoraProject;

import hu.progmatic.klad.KladService;
import hu.progmatic.klad.ParentKladDto;
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

    /*@Autowired
    private KladokService kladService;*/

    @Autowired
    private KladService kladService;

    // RETURN to .HTML-ek ---------------------------------------------------------------------------

    private String carnivora() {
        return "carnivoraProject/carnivora";
    }

    private String adatlap() {
        return "carnivoraProject/carnivoraAdatlap";
    }

    private String kartya() {
        return "carnivoraProject/talalatiKartyak";
    }

    // GET MAPPINGEK --------------------------------------------------------------------------------

    @RequestMapping("/")
    public String species() {
        return carnivora();
    }

    @GetMapping("/carnivoraProject/carnivora/{id}")
    public String szerkeszt(@PathVariable Integer id, Model model) {
        FajRecord formSpecies = carnivoraService.getById(id);
        model.addAttribute("formSpecies", formSpecies);
        return carnivora();
    }

    @GetMapping("/carnivoraProject/carnivora/{id}/adatlap")
    public String adatlapKiir(@PathVariable Integer id, Model model) {
        FajRecord formSpecies = carnivoraService.getById(id);
        model.addAttribute("formSpecies", formSpecies);
        return adatlap();
    }

    @GetMapping("/carnivoraProject/carnivora/{id}/TalalatiKartyak")
    public String kartyaKiIr(@PathVariable Integer id, Model model) {
        FajRecord formSpecies = carnivoraService.getById(id);
        model.addAttribute(formSpecies);
        return kartya();
    }

    @RequestMapping("/carnivoraProject/kozosos")
    public String kozosOS() {
        return "carnivoraProject/kozosos";
    }

    // POST MAPPINGEK --------------------------------------------------------------------------------

    @PostMapping("/carnivoraProject/carnivora/{id}")
    public String save(
            @PathVariable Integer id,
            @ModelAttribute("formSpecies") @Valid FajRecord formSpecies,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            formSpecies.setSzuloNev(carnivoraService.getSzuloNevBySzuloId(formSpecies.getSzuloId()));
            carnivoraService.save(formSpecies);
            refreshAllSpecies(model);
            clearFormItem(model);
        }
        return carnivora();
    }

    @PostMapping("/carnivoraProject/carnivora/")
    public String create(
            @ModelAttribute("formSpecies") @Valid FajRecord formSpecies,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            formSpecies.setSzuloNev(carnivoraService.getSzuloNevBySzuloId(formSpecies.getSzuloId()));
            carnivoraService.create(formSpecies);
            refreshAllSpecies(model);
            clearFormItem(model);
        }
        return carnivora();
    }

    @PostMapping("/carnivoraProject/carnivora/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        carnivoraService.deleteById(id);
        refreshAllSpecies(model);
        return carnivora();
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("allSpecies")
    List<FajRecord> allSpecies() {
        return carnivoraService.findAll();
    }

    @ModelAttribute("allKlad")
    List<ParentKladDto> allKlad() {
        return kladService.findAllParentKlad();
    }

    @ModelAttribute("formSpecies")
    public FajRecord formSpecies() {
        return carnivoraService.empty();
    }

    // MODEL MÓDOSÍTÓK -------------------------------------------------------------------------------

    private void refreshAllSpecies(Model model) {
        model.addAttribute("allSpecies", allSpecies());
    }

    private void clearFormItem(Model model) {
        model.addAttribute("formSpecies", formSpecies());
    }

}