package hu.progmatic.carnivora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Map;

@Controller
public class aboutController {

    @Autowired
    private CarnivoraService carnivoraService;

    @ModelAttribute("allAbout")
    Map<String, Object> allAbout() {
        return carnivoraService.jsonToMap();
    }
}
