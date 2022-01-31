package hu.progmatic.appconfig;

import hu.progmatic.carnivoraProject.Klad;
import hu.progmatic.carnivoraProject.KladService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
public class InitKladDataBase implements InitializingBean {

    private final KladService kladService;

    public InitKladDataBase(KladService kladService) {
        this.kladService = kladService;
    }

    @Override
    public void afterPropertiesSet() {
        if (kladService.findAll().isEmpty()) {
           Klad o1 = Klad.builder().nev("Ős-macskaformák alcsaládja").build();
            Klad k1 = Klad.builder().nev("párducformák alcsaládja").szulo(o1).build();
            Klad k2 = Klad.builder().nev("mongúzfélék családja").szulo(k1).build();
            Klad k3 = Klad.builder().nev("hiénafélék családja").szulo(k2).build();
            Klad k4 = Klad.builder().nev("cibetmacskafélék családja").szulo(k3).build();

//            o1 = Klad.builder().nev("Ős-kutyafélék családja").build();
//            k1 = Klad.builder().nev("bűzösborzfélék családja").szulo(o1).build();
//            k2 = Klad.builder().nev("vidraformák alcsaládja").szulo(k1).build();
//            k3 = Klad.builder().nev("borzformák alcsaládja").szulo(k2).build();
            kladService.saveAll(
                List.of(
                    o1, k1, k2, k3, k4
                )
            );
        }
    }
}
