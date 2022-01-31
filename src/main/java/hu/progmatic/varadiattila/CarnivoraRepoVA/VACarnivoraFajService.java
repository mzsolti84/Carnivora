package hu.progmatic.varadiattila.CarnivoraRepoVA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class VACarnivoraFajService {

    @Autowired
    VAKladRepository kladRepository;
    @Autowired
    VAFajRepository fajRepository;


    public VAKlad createKlad(VAKlad klad) {
        return kladRepository.save(klad);
    }

    public VAFaj createFaj(VAFaj faj) {
        return fajRepository.save(faj);
    }

    public String getParentNameById(Integer id) {
        return kladRepository.getById(id).getKladName();
    }

    public List<VAFaj> getFajByKladId(Integer kladId) {
        return kladRepository.getById(kladId).getFajok().stream().toList();
    }

    public VAKladDto getFullDto(Integer id) {
        VAKlad klad = kladRepository.getById(id);
        List<VAFajDto> fajokList = new ArrayList<>();
        for(VAFaj fajok : klad.getFajok()){
            VAFajDto fajDto = VAFajDto.builder()
                    .id(fajok.getId())
                    .name(fajok.getFajNev())
                    .szuloNev(fajok.getSzuloNev())
                    .build();
            fajokList.add(fajDto);
        }
        return VAKladDto.builder()
                .kladName(klad.getKladName())
                .fajok(fajokList)
                .build();
    }


    public void deleteFaj(VAFaj faj){
        fajRepository.delete(faj);
    }
    public void deleteKlad(VAKlad klad){
        fajRepository.deleteAll();
    }

    public void deleteAll(VAKlad klad) {
        VAKlad klad1 = kladRepository.getById(klad.getId());
        fajRepository.deleteAll(klad1.getFajok());
        kladRepository.delete(klad1);
    }

    public boolean kladExistById(Integer id) {
        return kladRepository.existsById(id);
    }
}
