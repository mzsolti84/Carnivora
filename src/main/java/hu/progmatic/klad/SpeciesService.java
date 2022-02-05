package hu.progmatic.klad;

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
}
