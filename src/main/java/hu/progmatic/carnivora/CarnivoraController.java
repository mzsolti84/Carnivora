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
    private FajService fajService;
    @Autowired
    private KladService kladService;
    @Autowired
    private KladForJsonService kladForJsonService;

    // GET MAPPINGEK --------------------------------------------------------------------------------

    @RequestMapping("/")
    public String belepes() {
        return "kezdolap";
    }


    @GetMapping("/faj_adatszerk/{id}")
    public String szerkeszt(@PathVariable Integer id, Model model) {
        FajDto formFajDto = fajService.getFajDtoByFajId(id);
        model.addAttribute("formFajDto", formFajDto);
        return "faj_adatszerk";
    }


    @GetMapping("/carnivora/{id}/adatlap")
    public String adatlapKiir(@PathVariable Integer id, Model model) {
        FajDto formFajDto = fajService.getFajDtoByFajId(id);
        model.addAttribute("formFajDto", formFajDto);
        return "carnivora_adatlap";
    }

    @GetMapping("/carnivora/{nevReszlet}")
    public String kereses(@PathVariable String nevReszlet, Model model) {
        List<FajDto> formFajDtoLista = fajService.getFajDtoByFajNevReszlet(nevReszlet);
        model.addAttribute("AllFajDto", formFajDtoLista);
        return "carnivora";
    }

    @RequestMapping("/kozosos")
    public String kozosOS() {
        return "kozos_os";
    }

    @RequestMapping("/carnivora")
    public String fajAdatbazis() {
        return "carnivora";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/genogramadmin")
    public String genogram() {
        return "genogram_admin";
    }

    @RequestMapping("/genogram")
    public String genogram_noAdmin() {
        return "genogram";
    }

    @RequestMapping("/faj_adatlista")
    public String faj_adatlista() {
        return "faj_adatlista";
    }

    @RequestMapping("/faj_adatszerk")
    public String faj_adatszerk() {
        return "faj_adatszerk";
    }

    // POST MAPPINGEK --------------------------------------------------------------------------------


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


    @PostMapping("/carnivora/")
    public String create(
            @ModelAttribute("formFajDto") @Valid FajDto formFajDto,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            fajService.create(formFajDto);
            model.addAttribute("allFajDto", allFajDto());
            model.addAttribute("formFajDto", formFajDto());
        }
        return "carnivora";
    }

    @PostMapping("/carnivora/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        fajService.deleteById(id);
        model.addAttribute("allFajDto", allFajDto());
        return "carnivora";
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("allFajDto")
    List<FajDto> allFajDto() {
        return fajService.getAllFajDto();
    }

    @ModelAttribute("allFajDtoSearch")
    List<FajDto> allFajDtoSearch() {
        return fajService.getAllFajDto();
    }

    @ModelAttribute("formFajDto")
    public FajDto formFajDto() {
        return new FajDto();
    }

    @ModelAttribute("allKlad")
    List<SzuloKladDto> allKlad() {
        return kladService.findAllParentKlad();
    }

    @ModelAttribute("jsonForGenogram")
    String getJsonForGenogram() {
        return kladForJsonService.getJsonForGenogram();
    }

}