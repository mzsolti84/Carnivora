package hu.progmatic.felhasznalo.token;

import hu.progmatic.felhasznalo.token.LejartTokenException;
import hu.progmatic.felhasznalo.token.MegerositoTokenService;
import hu.progmatic.felhasznalo.token.NemLetezoTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "felhasznalo/")
public class MegerositesController {


    @Autowired
    private MegerositoTokenService megerositoTokenService;

    @GetMapping(path = "confirm", produces = "text/plain; charset=UTF-8")
    public String confirm(@RequestParam("token") String token) {
        try{
            megerositoTokenService.megerosites(token);
            return "Sikeres megerősítés!";
        }catch (NemLetezoTokenException | LejartTokenException e){
            return e.getMessage();
        }

    }
}
