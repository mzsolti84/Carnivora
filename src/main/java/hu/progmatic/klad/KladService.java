package hu.progmatic.klad;

import hu.progmatic.iniklad.InitKladFromFileFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KladService implements InitializingBean {

    @Autowired
    private KladRepository kladRepository;

    @Override
    public void afterPropertiesSet() {
        if (kladRepository.count() == 0) {
            List<KladEntity> klads = new InitKladFromFileFactory(
                    "klad.csv")
                    .getKlads();
            kladRepository.saveAll(klads);
        }
    }

    public KladWithChildrenDto getKladDtoByName(String name) {
        KladEntity entity = kladRepository.findByNameEquals(name);

        return buildDto(entity);
    }

    private KladWithChildrenDto buildDto(KladEntity entity) {
        return KladWithChildrenDto.builder()
                .name(entity.getName())
                .latinName(entity.getLatinName())
                .description(entity.getDescription())
                .parent(entity.getParent().getName())
                .children(
                        entity.getChildren().stream()
                                .map(this::buildDto)
                                .toList()
                )
                .build();
    }

}
