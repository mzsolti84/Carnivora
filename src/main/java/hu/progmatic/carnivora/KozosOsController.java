package hu.progmatic.carnivora;


import hu.progmatic.felhasznalo.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Controller
public class KozosOsController {

    // AUTOWIRED-ek  --------------------------------------------------------------------------------

    @Autowired
    private GsonService gsonService;
    @Autowired
    private KladService kladService;
    @Autowired
    private FajService fajService;

    // REQUEST MAPPINGEK --------------------------------------------------------------------------------

    @RolesAllowed(UserType.Roles.USER_READ_ROLE)
    @RequestMapping("/kozososkereses")
    public String kozosOs() {
        return "kozos_os";
    }

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

            kozosOsDto.setJson(gsonService.getJsonForKozosOs(fajService.getById(kozosOsDto.getValasztottFaj1()), fajService.getById(kozosOsDto.getValasztottFaj2())).getJson());
        }
        return "kozos_os";
    }

    @GetMapping("/kozososadatlap/{id}")
    public String kozosOsAdatlapMeghivas(@PathVariable Integer id) {
        if (kladService.isExistsInRepositoryById(id)) {
            return "redirect:/klad_adatlap/"+id+"/adatlap";
        }
        return "redirect:/carnivora/"+id+"/adatlap";
    }

    // MODEL ATTRIBUTEOK -----------------------------------------------------------------------------

    @ModelAttribute("kozosOsDto")
    KozosOsDto kozosOsDto() {
        return KozosOsDto.builder()
                .fajDtoList(fajService.getAllFajDto())
                .valasztottFaj1(fajService.getByNev("Falanuk").getId())
                .valasztottFaj2(fajService.getByNev("Falanuk").getId())
                .kozosOs(new KladDto())
                .build();
    }
}