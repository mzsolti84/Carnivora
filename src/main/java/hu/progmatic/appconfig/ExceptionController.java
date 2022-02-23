package hu.progmatic.appconfig;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartException;

@Log4j2
@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(value = MultipartException.class)
  public String handleFileUploadException(MultipartException exception, Model model) {
    setTitle(model, "A megengedettnél nagyobb fájlt próbáltál feltölteni!");
    setDetails(model, exception.getMessage());
    log.info("File feltöltés hiba: " + exception.getMessage());
    return "hiba_oldal";
  }

  @ExceptionHandler(value = Exception.class)
  public String handleException(Exception exception, Model model) {
    log.error("Hiba a kérés feldolgozása közben!", exception);
    setDetails(model, exception.getMessage());
    return "hiba_oldal";
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  public String handleAccessDeniedException(AccessDeniedException exception, Model model) {
    log.error("Rossz helyre tévedtél!", exception);
    setDetails(model, "Rossz helyre tévedtél!");
    return "hiba_oldal";
  }

  @ModelAttribute("errorPageTitle")
  String errorPageTitle() {
    return "Hiba történt a kérés feldolgozása közben!";
  }

  @ModelAttribute("errorPageDetails")
  String errorPageDetails() {
    return "";
  }

  private void setDetails(Model model, String details) {
    model.addAttribute("errorPageDetails", details);
  }

  private void setTitle(Model model, String title) {
    model.addAttribute("errorPageTitle", title);
  }
}
