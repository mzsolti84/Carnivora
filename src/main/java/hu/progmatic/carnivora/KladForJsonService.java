package hu.progmatic.carnivora;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class KladForJsonService {
    @Autowired
    KladRepository kladRepository;

    // BACKEND -> JSON -> GENOGRAM irányba dolgozó metódusok, osztályok ------------------------------------------------

    private class JsonSourceForGsonDto {
        String classLenneDeNemLehetAz = "TreeModel";
        List<KladForJsonDto> nodeDataArray = getAllKladForJsonDto();
    }

    public String getJsonForGenogram() {
        Gson gson = new Gson();

        return gson.toJson(new JsonSourceForGsonDto()).replace("classLenneDeNemLehetAz", "class");
    }

    private List<KladForJsonDto> getAllKladForJsonDto() {
        return getAllKlad().stream()
                .map(klad -> buildKladForJsonDto(klad))
                .toList();
    }

    private List<Klad> getAllKlad() {
        return kladRepository.findAll();
    }

    private KladForJsonDto buildKladForJsonDto(Klad klad) {

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

    private KladForJsonDto buildKladForJsonDtoOrphan(Klad klad) {
        return KladForJsonDto.builder()
                .key(klad.getId())
                .name(capitalizeFirstLetter(klad.getNev()))
                .latinNev(capitalizeFirstLetter(klad.getLatinNev()))
                .parent(null)
                .build();
    }

    private KladForJsonDto buildKladForJsonDtoWithAncestor(Klad klad) {
        return KladForJsonDto.builder()
                .key(klad.getId())
                .name(capitalizeFirstLetter(klad.getNev()))
                .latinNev(capitalizeFirstLetter(klad.getLatinNev()))
                .parent(klad.getSzulo().getId())
                .build();
    }

    private String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    // GENOGRAM -> GSON -> BACKEND irányba dolgozó metódusok, osztályok ------------------------------------------------


}