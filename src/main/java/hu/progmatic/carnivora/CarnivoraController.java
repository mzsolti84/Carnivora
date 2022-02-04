package hu.progmatic.carnivora;

import hu.progmatic.klad.KladService;
import hu.progmatic.klad.ParentKladDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CarnivoraController {

    // AUTOWIRED-ek  --------------------------------------------------------------------------------

    @Autowired
    private SpeciesService speciesService;

    @Autowired
    private KladService kladService;

    // RETURN to .HTML-ek ---------------------------------------------------------------------------

    private String goToCarnivora() {
        return "carnivora/carnivora";
    }

    private String goToDataSheet() {
        return "carnivora/carnivoraAdatlap";
    }

    private String goToSearchResultCards() {
        return "carnivora/talalatiKartyak";
    }

    // GET MAPPINGEK --------------------------------------------------------------------------------

    @RequestMapping("/")
    public String species() {
        return goToCarnivora();
    }

    @GetMapping("/carnivoraProject/carnivora/{id}")
    public String editSpecies(@PathVariable Integer id, Model model) {
        Species formSpecies = speciesService.getById(id);
        model.addAttribute("formSpecies", formSpecies);
        return goToCarnivora();
    }

    @GetMapping("/carnivoraProject/carnivora/{id}/adatlap")
    public String getFormSpeciesForDataSheet(@PathVariable Integer id, Model model) {
        Species formSpecies = speciesService.getById(id);
        model.addAttribute("formSpecies", formSpecies);
        return "carnivora/carnivoraAdatlap";
    }

    @GetMapping("/carnivoraProject/carnivora/{id}/TalalatiKartyak")
    public String getFormSpeciesForSearchResultCards(@PathVariable Integer id, Model model) {
        Species formSpecies = speciesService.getById(id);
        model.addAttribute(formSpecies);
        return goToSearchResultCards();
    }

    @RequestMapping("/carnivoraProject/kozosos")
    public String goToCommonAncestor() {
        return "carnivoraProject/kozosos";
    }

    @RequestMapping("/carnivoraProject/kezdolap")
    public String goToIndexPage() {
        return "carnivoraProject/kezdolap";
    }

    // POST MAPPINGEK --------------------------------------------------------------------------------

    @PostMapping("/carnivoraProject/carnivora/{id}")
    public String save(
            @PathVariable Integer id,
            @ModelAttribute("formSpecies") @Valid Species formSpecies,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            //formSpecies.setSzuloNev(carnivoraService.getSzuloNevBySzuloId(formSpecies.getSzuloId()));
            speciesService.save(formSpecies);
            refreshAllSpecies(model);
            clearFormItem(model);
        }
        return goToCarnivora();
    }

    @PostMapping("/carnivoraProject/carnivora/")
    public String create(
            @ModelAttribute("formSpecies") @Valid Species formSpecies,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            //formSpecies.setSzuloNev(formSpecies.getSzuloNev());
            speciesService.create(formSpecies);
            refreshAllSpecies(model);
            clearFormItem(model);
        }
        return goToCarnivora();
    }

    @PostMapping("/carnivoraProject/carnivora/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        speciesService.deleteById(id);
        refreshAllSpecies(model);
        return goToCarnivora();
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("allSpecies")
    List<Species> allSpecies() {
        return speciesService.findAll();
    }

    @ModelAttribute("allClads")
    List<ParentKladDto> allClads() {
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