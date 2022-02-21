package hu.progmatic.carnivora;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StatisztikaServiceTest {

    @Autowired
    StatisztikaService statisztikaService;

    @Test
    void getStatistic() {
        assertNotNull(statisztikaService.getStatistic());
        assertNotNull(statisztikaService.getStatistic().getFajDb());
        assertNotNull(statisztikaService.getStatistic().getKladDb());
    }
}