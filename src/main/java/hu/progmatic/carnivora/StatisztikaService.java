package hu.progmatic.carnivora;

import hu.progmatic.carnivora.kepkezeles.KepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisztikaService {
    @Autowired
    private FajRepository fajRepository;
    @Autowired
    private KladRepository kladRepository;
    @Autowired
    private KepRepository kepRepository;

    public StatisztikaDto getStatistic() {
        StatisztikaDto statisztikaDto = new StatisztikaDto();
        statisztikaDto.setFajDb(Math.toIntExact(fajRepository.count()));
        statisztikaDto.setKladDb(Math.toIntExact(kladRepository.count()));
        statisztikaDto.setKepDb(Math.toIntExact(kepRepository.count()));
        return statisztikaDto;
    }
}
