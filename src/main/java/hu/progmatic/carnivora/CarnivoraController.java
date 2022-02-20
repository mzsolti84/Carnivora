package hu.progmatic.carnivora;


import hu.progmatic.felhasznalo.UserType;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private KladForGsonService kladForGsonService;

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

    @RolesAllowed(UserType.Roles.USER_READ_ROLE)
    @RequestMapping("/kozososkereses")
    public String kozosOs() {
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

    @RolesAllowed(UserType.Roles.USER_READ_ROLE)
    @PostMapping("/kozososkereses")
    public String kozosOsKereses(
            @ModelAttribute("kozosOsDto") @Valid KozosOsDto kozosOsDto,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            KladDto updateDomain = kladService.getFirstCommonKladAncestorOfFaj(
                    fajService.getById(kozosOsDto.getValasztottFaj1()), fajService.getById(kozosOsDto.getValasztottFaj2()));
            kozosOsDto.setKozosOs(updateDomain);

            kozosOsDto.setJson(kladForGsonService.getJsonForKozosOs(fajService.getById(kozosOsDto.getValasztottFaj1()), fajService.getById(kozosOsDto.getValasztottFaj2())).getJson());
        }
        return "kozos_os";
    }

    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
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

    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    @PostMapping("/faj_adatszerk")
    public String create(
            @ModelAttribute("formFajDto") @Valid FajDto formFajDto,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            fajService.create(formFajDto);
            model.addAttribute("allFajDto", allFajDto());
            model.addAttribute("formFajDto", formFajDto());
        }
        return "faj_adatlista";
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
        for (FajDto faj : fajService.getAllFajDto()) {
            System.out.println(faj.getNev() + faj.getKepId());
        }
        return fajService.getAllFajDto();
    }

    @ModelAttribute("kozosOsDto")
    KozosOsDto kozosOsDto() {
        return KozosOsDto.builder()
                .fajDtoList(fajService.getAllFajDto())
                .valasztottFaj1(fajService.getByNev("Falanuk").getId())
                .valasztottFaj2(fajService.getByNev("Falanuk").getId())
                .kozosOs(new KladDto())
                .build();
    }

    @ModelAttribute("jsonForGenogram")
    JsonForGenogramDto getJsonForGenogram() {
        return kladForGsonService.getJsonForGenogram();
    }
}