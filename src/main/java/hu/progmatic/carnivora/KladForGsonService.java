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
    KladRepository kladRepository;

    /// BACKEND -> JSON -> GENOGRAM irány ------------------------------------------------------------------------------

    // INNER CLASS -----------------------------------------------------------------------------------------------------

    private class DataForGson {
        String classLenneDeNemLehetAz = "TreeModel";
        List<KladForGsonDto> nodeDataArray = getAllKladForJsonDto();
    }

    // PUBLIC MAIN METÓDUS ----------------------------------------------------------------------------------------------------

    public JsonForGenogramDto getJsonForGenogram() {
        Gson gson = new Gson();

        return JsonForGenogramDto.builder()
                .json(gson.toJson(new DataForGson()).replace("classLenneDeNemLehetAz", "class"))
                .build();
    }

    // CLASS PRIVATE SEGÉDMETÓDUSOK

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

    /// GENOGRAM -> GSON -> BACKEND irányba dolgozó metódusok, osztályok ------------------------------------------------


}