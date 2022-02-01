package hu.progmatic.carnivoraProject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class KladokService implements InitializingBean {
    @Autowired
    private KladokRepository repository;
    @Autowired
    private ProbaKladRepository probaKladRepository;

    private final List<ProbaKlad> initKladNames = List.of(
        new ProbaKlad(null, "állatok világa", null, null),
        new ProbaKlad(null, "ragadozók rendje", "állatok világa", null),
        new ProbaKlad(null, "macskaalkatúak alrendje", "ragadozók rendje", null),
        new ProbaKlad(null, "madagaszkári cibetmacskafélék családja", "macskaalkatúak alrendje", null),
        new ProbaKlad(null, "macskafélék családja", "macskaalkatúak alrendje", null),
        new ProbaKlad(null, "macskaformák alcsaládja", "macskafélék családja", null),
        new ProbaKlad(null, "párducformák alcsaládja", "macskafélék családja", null),
        new ProbaKlad(null, "kardfogú macskák alcsaládja – kihalt", "macskafélék családja", null),
        new ProbaKlad(null, "mongúzfélék családja", "macskaalkatúak alrendje", null),
        new ProbaKlad(null, "hiénafélék családja", "macskaalkatúak alrendje", null),
        new ProbaKlad(null, "cibetmacskafélék családja", "macskaalkatúak alrendje", null),
        new ProbaKlad(null, "pálmacibetfélék családja", "macskaalkatúak alrendje", null),
        new ProbaKlad(null, "kutyaalkatúak alrendje", "ragadozók rendje", null),
        new ProbaKlad(null, "macskamedvefélék családja", "kutyaalkatúak alrendje", null),
        new ProbaKlad(null, "kutyafélék családja", "kutyaalkatúak alrendje", null),
        new ProbaKlad(null, "bűzösborzfélék családja", "kutyaalkatúak alrendje", null),
        new ProbaKlad(null, "menyétfélék családja", "kutyaalkatúak alrendje", null),
        new ProbaKlad(null, "vidraformák alcsaládja", "menyétfélék családja", null),
        new ProbaKlad(null, "borzformák alcsaládja", "menyétfélék családja", null),
        new ProbaKlad(null, "méhészborzformák alcsaládja", "menyétfélék családja", null),
        new ProbaKlad(null, "amerikai borzformák alcsaládja", "menyétfélék családja", null),
        new ProbaKlad(null, "menyétformák alcsaládja", "menyétfélék családja", null),
        new ProbaKlad(null, "mosómedvefélék családja", "kutyaalkatúak alrendje", null),
        new ProbaKlad(null, "medvefélék családja", "kutyaalkatúak alrendje", null),
        new ProbaKlad(null, "úszólábúak öregcsaládja", "kutyaalkatúak alrendje", null),
        new ProbaKlad(null, "rozmárfélék családja", "úszólábúak öregcsaládja", null),
        new ProbaKlad(null, "fülesfókafélék családja", "úszólábúak öregcsaládja", null),
        new ProbaKlad(null, "valódi fókafélék családja", "úszólábúak öregcsaládja", null)
    );

    public void init(){
        Klad o1 = save(Klad.builder().nev("Ős-macskaformák alcsaládja").build());
        Klad k1 = save(Klad.builder().nev("párducformák alcsaládja").szulo(o1).build());
        Klad k2 = save(Klad.builder().nev("mongúzfélék családja").szulo(k1).build());
        Klad k3 = save(Klad.builder().nev("hiénafélék családja").szulo(k2).build());
        Klad k4 = save(Klad.builder().nev("cibetmacskafélék családja").szulo(k3).build());

        o1 = save(Klad.builder().nev("Ős-kutyafélék családja").build());
        k1 = save(Klad.builder().nev("bűzösborzfélék családja").szulo(o1).build());
        k2 = save(Klad.builder().nev("vidraformák alcsaládja").szulo(k1).build());
        k3 = save(Klad.builder().nev("borzformák alcsaládja").szulo(k2).build());
    }

    public List<Klad> findAll(){
        return repository.findAll();
    }

    public Klad save(Klad data) {
        return repository.saveAndFlush(data);
    }

    public void saveAll(List<Klad> kladList) {
        repository.saveAllAndFlush(kladList);
    }

    public List<Klad> findAllWithNoChild() {
        return repository.findAllWithNoChild();
    }

    public Klad getById(Integer id) {
        return repository.getById(id);
    }

    public List<ProbaKlad> findAllPureNames() {
        return probaKladRepository.findAll();
    }

    @Override
    public void afterPropertiesSet() {
        if (probaKladRepository.count() == 0) {
            probaKladRepository.saveAll(initKladNames);
        }
    }
}
