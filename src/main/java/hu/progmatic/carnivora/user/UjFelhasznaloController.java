package hu.progmatic.carnivora.user;

import hu.progmatic.felhasznalo.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UjFelhasznaloController {


    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/ujfelhasznalo/kuld/")
    public String kuld(Model model){
        emailSenderService.emailKuldes("carnivora.project@gmail.com", "teszt 1", "first test message");
        return "ujfelhasznalo";
    }
}
