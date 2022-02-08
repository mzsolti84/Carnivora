package hu.progmatic.carnivora;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CarnivoraService {

    @Autowired
    private FajRepository fajRepositoryC;

    public List<Faj> findAll() {
        return fajRepositoryC.findAll();
    }

    public Faj create(Faj faj) {
        faj.setId(null);
        return fajRepositoryC.save(faj);
    }

    public Faj save(Faj faj) {
        return fajRepositoryC.saveAndFlush(faj);
    }

    public void deleteById(Integer id) {
        fajRepositoryC.deleteById(id);
    }

    public Faj getById(Integer id) {
        return fajRepositoryC.getById(id);
    }

    public void deleteByIdIfExists(Integer id) {
        if (fajRepositoryC.existsById(id)) {
            fajRepositoryC.deleteById(id);
        }
    }

    public Faj findById(Integer id) {
        return fajRepositoryC.findById(id)
                .orElseThrow();
    }

}