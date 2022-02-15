package hu.progmatic.carnivora;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KladForGsonService {

    @Autowired
    private KladRepository kladRepository;

    private class DataForGson {
        String classLenneDeNemLehetAz = "TreeModel";
        List<KladForGsonDto> nodeDataArray = getAllKladForJsonDto();
    }

    private Gson gson = new Gson();

    /// PUBLIC MAIN METÓDUSOK ------------------------------------------------------------------------------------------

    public JsonForGenogramDto getJsonForGenogram() {
        return JsonForGenogramDto.builder()
                .json(gson.toJson(new DataForGson()).replace("classLenneDeNemLehetAz", "class"))
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
    // CLASS PRIVATE SEGÉDMETÓDUSOK ------------------------------------------------------------------------------------

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
        Exception exception = null;

        try {
            klad.getSzulo().getId();
        } catch (Exception e) {
            exception = e;
        }

        return exception != null;
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
    // CLASS PRIVATE SEGÉDMETÓDUSOK ------------------------------------------------------------------------------------

    private DataForGson getDataForGsonFromJson(JsonForGenogramDto json) {
        return gson.fromJson(json.getJson().replace("class", "classLenneDeNemLehetAz"), DataForGson.class);
    }

}