package hu.progmatic.carnivora;

import hu.progmatic.carnivora.kepkezeles.KepDto;
import hu.progmatic.carnivora.kepkezeles.KepMegjelenitesDto;
import hu.progmatic.carnivora.kepkezeles.KepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class KepAdatController {
    @Autowired
    KepService service;

    @RequestMapping("/kepadat")
    public String kepadat(){ return "kepadat";}

    @GetMapping("/kepadat/{kepId}")
    public void getKepAdat2(@PathVariable Integer kepId, HttpServletResponse response) throws IOException {
        KepMegjelenitesDto dto = service.getKepMegjelenitesDto(kepId);
        response.setContentType(dto.getContentType());
        response.getOutputStream().write(dto.getKepAdat());
    }

    @ModelAttribute("kepek2")
    List<KepDto> getAllKepDto() {
        return service.getAllKepDto();
    }
}
