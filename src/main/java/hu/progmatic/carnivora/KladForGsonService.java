package hu.progmatic.carnivora;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KladForGsonService {
    private final Gson gson = new Gson();

    @Autowired
    private KladRepository kladRepository;

    private class DataForGson {
        // a genogram Json inputnak szüksége van a classLenneDeNemLehetAz változóra
        @SuppressWarnings("unused")
        @SerializedName("class")
        String classLenneDeNemLehetAz = "TreeModel";
        List<KladForGsonDto> nodeDataArray = getAllKladForJsonDto();
    }

    public JsonForGenogramDto getJsonForGenogram() {
        return JsonForGenogramDto.builder()
                .json(gson.toJson(new DataForGson()))
                .build();
    }

    public void updateKladRepository(JsonForGenogramDto jsonForGenogramDto) {
        List<Klad> allKlad = getAllKlad();
        DataForGson dataForGson = getDataForGsonFromJson(jsonForGenogramDto);

        allKlad.stream()
                .forEach(klad -> klad.setNev(dataForGson.nodeDataArray.stream()
                        .filter(kladForGsonDto -> kladForGsonDto.getKey() == klad.getId())
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

    private DataForGson getDataForGsonFromJson(JsonForGenogramDto json) {
        return gson.fromJson(json.getJson(), DataForGson.class);
    }

}