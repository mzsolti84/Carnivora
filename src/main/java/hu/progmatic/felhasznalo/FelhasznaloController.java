package hu.progmatic.felhasznalo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
public class FelhasznaloController {
  private final FelhasznaloService felhasznaloService;

  public FelhasznaloController(FelhasznaloService felhasznaloService) {
    this.felhasznaloService = felhasznaloService;
  }

  @GetMapping("/ujfelhasznalo")
  public String ujFelhasznalo() {
    return "ujfelhasznalo";
  }

  @GetMapping("/felhasznalo")
  public String lista() {
    return "felhasznalo";
  }

  @PostMapping("/felhasznalo")
  public String add(@ModelAttribute UjFelhasznaloCommand command, Model model) {
    model.addAttribute("ujFelhasznaloError", null);
    try {
      felhasznaloService.add(command);
    } catch (FelhasznaloLetrehozasException e) {
      model.addAttribute("ujFelhasznaloError", e.getMessage());
      return "felhasznalo";
    }
    frissit(model);
    return "felhasznalo";
  }

  private void frissit(Model model) {
    model.addAttribute("allFelhasznalo", populateTypes());
  }

  @PostMapping("/felhasznalo/delete/{id}")
  public String delete(@PathVariable Long id, Model model) {
    felhasznaloService.delete(id);
    frissit(model);
    return "redirect:/felhasznalo";
  }

  @ModelAttribute("allFelhasznalo")
  public List<Felhasznalo> populateTypes() {
    if (felhasznaloService.hasRole(UserType.Roles.USER_READ_ROLE)) {
      return felhasznaloService.findAll();
    }
    return List.of();
  }

  @ModelAttribute("ujFelhasznaloCommand")
  public UjFelhasznaloCommand ujFelhasznaloCommand() {
    return new UjFelhasznaloCommand();
  }

  @ModelAttribute("ujFelhasznaloError")
  public String ujFelhasznaloError() {
    return null;
  }

  @ModelAttribute("hasUserWriteRole")
  public boolean userWriteRole() {
    return felhasznaloService.hasRole(UserType.Roles.USER_WRITE_ROLE);
  }

  @ModelAttribute("hasUserReadRole")
  public boolean userReadRole() {
    return felhasznaloService.hasRole(UserType.Roles.USER_READ_ROLE);
  }

  @ModelAttribute("userId")
  public Long userId() {
    return felhasznaloService.getFelhasznaloId();
  }

  @ModelAttribute("userName")
  public String userName() {
    return felhasznaloService.getFelhasznaloNev();
  }

  @ModelAttribute("allRole")
  public List<String> allRole() {
    return Arrays.stream(UserType.values())
        .map(UserType::name).toList();
  }

  @PostMapping("/ujfelhasznalo/letrehozas")
  public String letrehozas(@ModelAttribute("ujFelhasznaloCommand") @Valid UjFelhasznaloCommand command,
                           BindingResult bindingResult,
                           Model model) {
    try {
      felhasznaloService.ujFelhasznaloValidalas(command);
    } catch (FelhasznaloLetrehozasException e) {
      bindingResult.addError(new FieldError("ujFelhasznaloCommand",
              "felhasznaloNev",
              e.getMessage()));
    }
    if (!bindingResult.hasErrors()){
      try{
        felhasznaloService.add(command);
        return "kezdolap";
      }catch (FelhasznaloLetrehozasException e){
        model.addAttribute("emailKuldesHiba", e.getMessage());
        return "ujfelhasznalo";
      }
    }
    return "ujfelhasznalo";
  }

  @ModelAttribute("emailKuldesHiba")
  public String emailKuldesHiba() {
    return null;
  }

}
