package hu.progmatic.klad;

import hu.progmatic.carnivoraProject.ProbaKlad;
import hu.progmatic.iniklad.InitKladFromFileFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

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

        return buildKladWithChildrenDto(entity);
    }

    private KladWithChildrenDto buildKladWithChildrenDto(KladEntity entity) {
        return KladWithChildrenDto.builder()
                .name(entity.getName())
                .latinName(entity.getLatinName())
                .description(entity.getDescription())
                .parent(entity.getParent().getName())
                .children(
                        entity.getChildren().stream()
                                .map(this::buildKladWithChildrenDto)
                                .toList()
                )
                .build();
    }

    public List<KladWithChildrenDto> findAllWithNoChild() {
        List<KladEntity> noChildrens = kladRepository.findAllWithNoChild();
        return noChildrens.stream().map(this::buildKladWithChildrenDto).toList();
    }

    public String getJsonFromKladEntityList(List<KladEntity> allKladEntity) {
        return """
                { "class": "TreeModel",\\n
                  "nodeDataArray": [\\n
                """
                +
                String.join(",\\n", allKladEntity.stream()
                        .map(kladEntity -> new Gson().toJson(kladEntity))
                        .toList())
                +
                """
                        \\n
                        ]}
                        """;
    }

    public List<ParentKladDto> findAllParentKlad() {
        return kladRepository.findAllWithNoChild().stream()
                .map(this::kladToParentKladDto)
                .toList();
    }

    private ParentKladDto kladToParentKladDto(KladEntity klad) {
        return ParentKladDto.builder()
                .id(klad.getId())
                .name(klad.getName())
                .build();
    }
}
