package hu.progmatic.carnivora.kepkezeles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class KepController {
  @Autowired
  KepService service;

  @RequestMapping("/kepkezeles")
  public String kepkezeles(Model model) {
    return "kep/kep";
  }

  @PostMapping("/kepkezeles")
  public String submit(
      @ModelAttribute("kepFeltoltesCommand") @Valid KepFeltoltesCommand kepFeltoltesCommand,
      BindingResult bindingResult
  ) {
    if (!bindingResult.hasErrors()) {
      service.saveKep(kepFeltoltesCommand);
      return "redirect:/kepkezeles";
    }
    return "kep/kep";
  }

  @PostMapping("/kepkezeles/delete/{kepId}")
  public String delete(
      @PathVariable Integer kepId
  ) {
    service.deleteKep(kepId);
    return "redirect:/kepkezeles";
  }

  @GetMapping("/kepkezeles/{kepId}")
  public void getKepAdat(@PathVariable Integer kepId, HttpServletResponse response) throws IOException {
    KepMegjelenitesDto dto = service.getKepMegjelenitesDto(kepId);
    response.setContentType(dto.getContentType());
    response.getOutputStream().write(dto.getKepAdat());
  }

  @ModelAttribute("kepFeltoltesCommand")
  KepFeltoltesCommand kepFeltoltesCommand() {
    return new KepFeltoltesCommand();
  }

  @ModelAttribute("kepek")
  List<KepDto> getAllKepDto() {
    return service.getAllKepDto();
  }
}
