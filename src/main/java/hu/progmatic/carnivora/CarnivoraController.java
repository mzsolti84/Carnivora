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
    private FajService fajService;
    @Autowired
    private KladService kladService;

    // GET MAPPINGEK --------------------------------------------------------------------------------

    @RequestMapping ("/")
    public String belepes() {
        return "kezdolap";
    }

    /*@GetMapping("/carnivora/{id}")
    public String szerkeszt(@PathVariable Integer id, Model model) {
        Faj formFaj = carnivoraService.getById(id);
        model.addAttribute("formFaj", formFaj);
        return "carnivora";
    }*/

    @GetMapping("/carnivora/{id}")
    public String szerkeszt(@PathVariable Integer id, Model model) {
        FajDto formFajDto = fajService.getById(id);
        model.addAttribute("formFajDto", formFajDto);
        return "carnivora";
    }


    @GetMapping("/carnivora/{id}/adatlap")
    public String adatlapKiir(@PathVariable Integer id, Model model) {
        FajDto formFajDto = fajService.getById(id);
        model.addAttribute("formFajDto", formFajDto);
        return "carnivora_adatlap";
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
        return "genogramadmin";
    }

    // POST MAPPINGEK --------------------------------------------------------------------------------

    /*@PostMapping("/carnivora/{id}")
    public String save(
            @PathVariable Integer id,
            @ModelAttribute("formFaj") @Valid Faj formFaj,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            carnivoraService.save(formFaj);
            model.addAttribute("allFaj", allFaj());
            model.addAttribute("formFaj", formFaj());
        }
        return "carnivora";
    }*/

    @PostMapping("/carnivora/{id}")
    public String save(
            @PathVariable Integer id,
            @ModelAttribute("formFajDto") @Valid FajDto formFajDto,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            fajService.save(formFajDto);
            model.addAttribute("allFajDto", allFajDto());
            model.addAttribute("formFajDto", formFajDto());
        }
        return "carnivora";
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
        carnivoraService.deleteById(id);
        model.addAttribute("allFaj", allFaj());
        return "carnivora";
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("allFaj")
    List<Faj> allFaj() {
        return carnivoraService.findAll();
    }

    @ModelAttribute("allFajDto")
    List<FajDto> allFajDto() {
        for (FajDto lista : fajService.getAllFajDto()) {
            if (lista.getSzuloNev() != null) System.out.println(lista.getSzuloNev() + " " + lista.getSzuloId());
        }
        return fajService.getAllFajDto();
    }

    @ModelAttribute("formFaj")
    public Faj formFaj() {
        return new Faj();
    }

    @ModelAttribute("formFajDto")
    public FajDto formFajDto() {
        return new FajDto();
    }

    @ModelAttribute("allKlad")
    List<SzuloKladDto> allKlad() {
        return kladService.findAllParentKlad();
    }

}