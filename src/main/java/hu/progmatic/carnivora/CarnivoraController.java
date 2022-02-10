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

    @RequestMapping("/")
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
        FajDto formFajDto = fajService.getFajDtoByFajId(id);
        model.addAttribute("formFajDto", formFajDto);
        return "carnivora";
    }


    @GetMapping("/carnivora/{id}/adatlap")
    public String adatlapKiir(@PathVariable Integer id, Model model) {
        FajDto formFajDto = fajService.getFajDtoByFajId(id);
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
        return "genogram_admin";
    }

    @RequestMapping("/genogram")
    public String genogram_noAdmin() {
        return "geno_gram";
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
            //FajDto fajDto = fajService.getById(id);
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
        fajService.deleteById(id);
        model.addAttribute("allFajDto", allFajDto());
        return "carnivora";
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("allFajDto")
    List<FajDto> allFajDto() {
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

}