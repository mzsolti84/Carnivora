package hu.progmatic.service;

import hu.progmatic.carnivora.kepkezeles.KepFeltoltesCommand;
import hu.progmatic.carnivora.kepkezeles.KepService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class KepServiceTest {
  @Autowired
  KepService kepService;

  //KepService kepService = new KepService();

  @Test
  void meretFormazas() {
    assertEquals("21 B", kepService.meretFormazas(21L));
    assertEquals("21 KB", kepService.meretFormazas(21L * 1024));
    assertEquals("21 MB", kepService.meretFormazas(21L * 1024 * 1024));
  }

  @Test
  void saveKepError() {
    KepFeltoltesCommand kepFeltoltesCommand = new KepFeltoltesCommand();
    kepFeltoltesCommand.setMegnevezes("Kep.jpg");
    kepFeltoltesCommand.setKepFile(null);

    Exception exception = null;
    try {
      kepService.saveKep(kepFeltoltesCommand);
    } catch (Exception e) {
      exception = e;
    }
    assertNotNull(exception);
    assertEquals("Cannot invoke \"org.springframework.web.multipart.MultipartFile.getContentType()\" because \"kepFile\" is null",
            exception.getMessage());
  }
}