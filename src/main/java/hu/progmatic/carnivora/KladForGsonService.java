package hu.progmatic.carnivora;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class KladForGsonService {
    private final Gson gson = new Gson();

    @Autowired
    private KladRepository kladRepository;
    @Autowired
    private KladService kladService;

    private class DataForGson {
        // a genogram Json inputnak szüksége van a classLenneDeNemLehetAz változóra
        @SuppressWarnings("unused")
        @SerializedName("class")
        String classLenneDeNemLehetAz = "TreeModel";
        List<KladForGsonDto> nodeDataArray = getAllKladForJsonDto();
    }
    private class KozosOsBloodLineDataForGson {
        // a genogram Json inputnak szüksége van a classLenneDeNemLehetAz változóra
        @SuppressWarnings("unused")
        @SerializedName("class")
        String classLenneDeNemLehetAz = "TreeModel";
        Set<NodeForBloodLineDto> nodeDataArray;

        public KozosOsBloodLineDataForGson(Set<NodeForBloodLineDto> nodeDataArray) {
            this.nodeDataArray = nodeDataArray;
        }
    }

    public JsonForGenogramDto getJsonForGenogram() {
        return JsonForGenogramDto.builder()
                .json(gson.toJson(new DataForGson()))
                .build();
    }

    public JsonForGenogramDto getJsonForKozosOs(Faj faj1, Faj faj2) {
        return JsonForGenogramDto.builder()
                .json(gson.toJson(new KozosOsBloodLineDataForGson(kladService.getBloodLineDtoForKozosOs(faj1, faj2))))
                .build();
    }

    public void updateKladRepository(JsonForGenogramDto jsonForGenogramDto) {
        List<Klad> allKlad = getAllKlad();
        KozosOsBloodLineDataForGson dataForGson = getDataForGsonFromJson(jsonForGenogramDto);

        allKlad.forEach(klad -> klad.setNev(dataForGson.nodeDataArray.stream()
                .filter(kladForGsonDto -> kladForGsonDto.getKey().equals(klad.getId()))
                .toList().get(0).getName()));
    }

    /// BACKEND -> JSON -> GENOGRAM irány ------------------------------------------------------------------------------

    private List<KladForGsonDto> getAllKladForJsonDto() {
        return getAllKlad().stream()
                .map(klad -> buildKladForJsonDto(klad))
                .toList();
    }

    private List<Klad> getAllKlad() {
        return kladRepository.findAll();
    }

    private KladForGsonDto buildKladForJsonDto(Klad klad) {
        if (isOrphan(klad)) {
            return buildKladForJsonDtoOrphan(klad);
        } else {
            return buildKladForJsonDtoWithAncestor(klad);
        }
    }

    private boolean isOrphan(Klad klad) {
        return klad.getSzulo() == null;
    }

    private KladForGsonDto buildKladForJsonDtoOrphan(Klad klad) {
        return KladForGsonDto.builder()
                .key(klad.getId())
                .name(capitalizeFirstLetter(klad.getNev()))
                .latinNev(capitalizeFirstLetter(klad.getLatinNev()))
                .parent(null)
                .build();
    }

    private KladForGsonDto buildKladForJsonDtoWithAncestor(Klad klad) {
        return KladForGsonDto.builder()
                .key(klad.getId())
                .name(capitalizeFirstLetter(klad.getNev()))
                .latinNev(capitalizeFirstLetter(klad.getLatinNev()))
                .parent(klad.getSzulo().getId())
                .build();
    }

    private String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /// GENOGRAM -> GSON -> BACKEND irányba dolgozó metódusok, osztályok -----------------------------------------------

    private KozosOsBloodLineDataForGson getDataForGsonFromJson(JsonForGenogramDto json) {
        return gson.fromJson(json.getJson(), KozosOsBloodLineDataForGson.class);
    }

}