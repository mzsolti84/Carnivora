package hu.progmatic.service;

import hu.progmatic.carnivora.kepkezeles.KepService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KepServiceTest {
  KepService kepService = new KepService();

  @Test
  void meretFormazas() {
    assertEquals("21 B", kepService.meretFormazas(21L));
    assertEquals("21 KB", kepService.meretFormazas(21L * 1024));
    assertEquals("21 MB", kepService.meretFormazas(21L * 1024 * 1024));
  }
}