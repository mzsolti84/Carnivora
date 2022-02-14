package hu.progmatic.carnivora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KladController {
    // AUTOWIRED-ek  --------------------------------------------------------------------------------
    @Autowired
    private KladService kladService;

    @RequestMapping("/klad_adatlap")
    public String klad_belepes() {
        return "klad_adatlap";
    }

    @GetMapping("/klad_adatlap/{id}/adatlap")
    public String adatlapKiir(@PathVariable Integer id, Model model) {
        KladDto formKladDto = kladService.getKladDtoById(id);
        model.addAttribute("formKladDto", formKladDto);
        return "klad_adatlap";
    }

    @ModelAttribute("formKladDto")
    public KladDto formKladDto() {
        return new KladDto();
    }
}
