package hu.progmatic.carnivora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SpeciesService {


    @Autowired
    private SpeciesRepository speciesRepository;

    public void saveAll(List<Species> species){
        speciesRepository.saveAll(species);
    }

    public SpeciesTestDto speciesToSpeciesTestDto(String nev){
        Species species = speciesRepository.getByNev(nev);
        return SpeciesTestDto.builder()
                .name(species.getNev())
                .parentName(species.getKlad().getNev())
                .description(species.getLeiras())
                .turesHatar(species.getTuresHatar())
                .veszelyeztetettBesorolas(species.getVeszelyeztetettBesorolas())
                .build();
    }
}
