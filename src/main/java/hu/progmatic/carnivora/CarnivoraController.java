package hu.progmatic.carnivora;


import hu.progmatic.carnivora.kepkezeles.ment.KepMent;
import hu.progmatic.felhasznalo.UserType;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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
    private GsonService gsonService;
    @Autowired
    private StatisztikaService statisztikaService;


    // GET MAPPINGEK --------------------------------------------------------------------------------

    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
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

    @RequestMapping("/kezdolap")
    public String kezdolap() {
        return "/kezdolap";
    }

    @RequestMapping("/carnivora")
    public String fajAdatbazis() {
        return "carnivora";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    @RequestMapping("/faj_adatlista")
    public String faj_adatlista() {
        return "faj_adatlista";
    }

    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    @RequestMapping("/faj_adatszerk")
    public String faj_adatszerk() {
        return "faj_adatszerk";
    }

    @RequestMapping("/veszelyeztetett")
    public String veszelyeztetett() {
        return "veszelyeztetett";
    }

    // POST MAPPINGEK --------------------------------------------------------------------------------


    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    @PostMapping("/faj_adatszerk/{id}")
    public String update(
            @PathVariable Integer id,
            @ModelAttribute("formFajDto") @Valid FajDto formFajDto,
            BindingResult bindingResult,
            Model model) {
        try {
            fajService.latinNevValidacio(formFajDto.getLatinNev(), id);
        }catch (NemUniqueLatinNevException e){
            bindingResult.addError(new FieldError("formFajDto", "latinNev", e.getMessage()));
        }
        if (!bindingResult.hasErrors()) {
            //FajDto fajDto = fajService.getById(id);
            fajService.save(formFajDto);
            model.addAttribute("allFajDto", allFajDto());
            model.addAttribute("formFajDto", formFajDto());
            return "faj_adatlista";
        }
        return "/faj_adatszerk";
    }

    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    @PostMapping("/faj_adatszerk")
    public String create(
            @ModelAttribute("formFajDto") @Valid FajDto formFajDto,
            BindingResult bindingResult,
            Model model) {
        try {
            fajService.latinNevValidacio(formFajDto.getLatinNev());
        }catch (NemUniqueLatinNevException e){
            bindingResult.addError(new FieldError("formFajDto", "latinNev", e.getMessage()));
        }
        if (!bindingResult.hasErrors()) {
            fajService.create(formFajDto);
            model.addAttribute("allFajDto", allFajDto());
            model.addAttribute("formFajDto", formFajDto());
            return "faj_adatlista";
        }
        return "faj_adatszerk";
    }

    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    @PostMapping("/faj_adatlista/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        fajService.deleteById(id);
        model.addAttribute("allFajDto", allFajDto());
        return "faj_adatlista";
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

    @ModelAttribute("allSpecies")
    List<FajDto> allSpecies() {
        return fajService.getAllFajDto();
    }

    @ModelAttribute("jsonForGenogram")
    JsonForGenogramDto getJsonForGenogram() {
        return gsonService.getJsonForGenogram();
    }

    @ModelAttribute("statisztika")
    StatisztikaDto statisztika() {
        if (statisztikaService.getStatistic() != null) {
        return statisztikaService.getStatistic(); }
        else
            return StatisztikaDto.builder()
                    .fajDb(0)
                    .kladDb(0)
                    .kepDb(0)
                    .build();
    }
}