package hu.progmatic.carnivoraProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class KladBevitelController {

    @Autowired
    private KladService kladService;

    @GetMapping("/carnivoraProject/kladbevitel")
    public String kladStarter(Model model) {
        //KladForm form = new KladForm();
        //model.addAttribute("kladForm", form);
        return "/carnivoraProject/kladbevitel";
    }

    @GetMapping("/carnivoraProject/kladbevitel/a")
    public String kereses(
            @ModelAttribute("kladForm") KladForm kladForm,
            Model model) {
        model.addAttribute("isKivalasztottSzulo", true);
        model.addAttribute("isIni", true);
        model.addAttribute("szuloNev", kladForm.klad.nev);
        model.addAttribute("formKladBevitel",
            KladBevitelForm.builder()
                .szuloId(kladForm.klad.id)
                .build()
        );
        model.addAttribute("kladForm", kladForm);
        return kladStarter(model);
    }


    @PostMapping("/carnivoraProject/kladbevitel/bevitel")
    public String save(
            @ModelAttribute("formKladBevitel") @Valid KladBevitelForm kladBevitelForm,
            @ModelAttribute("kladForm")  KladForm kladForm,
            BindingResult bindingResult,
            Model model) {
        if (!bindingResult.hasErrors()) {
            Klad szulo = kladService.getById(kladBevitelForm.getSzuloId());
            kladService.save(Klad.builder()
                            .nev(kladBevitelForm.megnevezes)
                            .szulo(szulo)
                            .build());
            model.addAttribute("noChildrenNodes", noChildrenNodes());
            model.addAttribute("noChildrenNodes", noChildrenNodes());
            model.addAttribute("allKlad", allKlad());
        }
        return kladStarter(model);
    }


    @ModelAttribute("noChildrenNodes")
    public List<Klad> noChildrenNodes(){
        return kladService.findAllWithNoChild();
    }

    @ModelAttribute("allKlad")
    public List<Klad> allKlad(){
        return kladService.findAll();
    }


    @ModelAttribute("kladForm")
    public KladForm kladFormForm(){
        return new KladForm();
    }

    @ModelAttribute("isKivalasztottSzulo")
    public boolean isKivalasztottSzulo(){
        return false;
    }

    @ModelAttribute("formKladBevitel")
    public KladBevitelForm ujKladSave(){
        return new KladBevitelForm();
    }

    @ModelAttribute("isIni")
    public boolean isIni(){
        return false;
    }

    @PostMapping("/carnivoraProject/kladbevitel/ini")
    public String ini(Model model){
        if (kladService.findAll().isEmpty()) {
            kladService.init();
        }
        model.addAttribute("isIni",true);
        model.addAttribute("noChildrenNodes", noChildrenNodes());
        model.addAttribute("allKlad", allKlad());
        return "/carnivoraProject/kladbevitel";
    }
}
