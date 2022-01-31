package hu.progmatic.carnivoraProject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CarnivoraService implements InitializingBean {
    @Autowired
    private SpeciesRepository speciesRepository;
    @Autowired
    private ProbaKladRepository probaKladRepository;

    private final List<FajRecord> initSpecies = List.of(
            new FajRecord(null,
                    4,
                    "madagaszkári cibetmacskafélék családja",
                    "Madagaszkáron él és szereti a makit.",
                    "Fossza",
                    "Cryptoprocta ferox",
                    VeszelyeztetettKategoriak.SEBEZHETO,
                    Tureshatar.SPECIALISTA,
                    "https://upload.wikimedia.org/wikipedia/commons/8/8b/Cryptoprocta_ferox_-_1700-1880_-_Print_-_Iconographia_Zoologica_-_Special_Collections_University_of_Amsterdam_-%28white_background%29.png",
                    null),
            new FajRecord(null,
                    6,
                    "macskaformák alcsaládja",
                    "A macskák élvezik a meleget és a napsütést, gyakran alszanak napos helyeken. Magasabb hőmérsékleten érzik jól magukat, mint az emberek, akik számára a 44,5 °C-nál nagyobb bőrfelszíni hőmérséklet már nehezen elviselhető, míg a macskák akkor kezdik kellemetlenül érezni magukat, ha a bőrük 52 °C-ra melegszik.",
                    "Házimacska",
                    "Felis silvestris catus",
                    VeszelyeztetettKategoriak.HAZIASITOTT,
                    Tureshatar.SPECIALISTA,
                    "https://upload.wikimedia.org/wikipedia/commons/e/e7/Jammlich_crop.jpg",
                    "https://hu.wikipedia.org/wiki/Macska"),
            new FajRecord(null,
                    4,
                    "madagaszkári cibetmacskafélék családja",
                    "Spicces macska",
                    "Falanuk",
                    "Eupleres goudotii",
                    VeszelyeztetettKategoriak.FENYEGETETT,
                    Tureshatar.GENERALISTA,
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/EupleresGoudotiSmit.jpg/2560px-EupleresGoudotiSmit.jpg",
                    "https://hu.wikipedia.org/wiki/Falanuk"),

            new FajRecord(null,
                    15,
                    "kutyafélék családja",
                    "Farkas",
                    "Szürke farkas",
                    "Canis lupus",
                    VeszelyeztetettKategoriak.FENYEGETETT,
                    Tureshatar.GENERALISTA,
                    "https://upload.wikimedia.org/wikipedia/commons/5/5f/Kolm%C3%A5rden_Wolf.jpg",
                    "https://hu.wikipedia.org/wiki/Sz%C3%BCrke_farkas")

    );

    public List<FajRecord> findAll() {
        return speciesRepository.findAll();
    }

    public FajRecord create(FajRecord faj) {
        faj.setId(null);
        return speciesRepository.save(faj);
    }

    public FajRecord save(FajRecord faj) {
        return speciesRepository.saveAndFlush(faj);
    }

    public void deleteById(Integer id) {
        speciesRepository.deleteById(id);
    }

    public FajRecord getById(Integer id) {
        return speciesRepository.getById(id);
    }

    public String getSzuloNevBySzuloId(Integer id) {
        return probaKladRepository.getById(id).getNev();
    }

    public FajRecord empty() {
        return new FajRecord();
    }

    @Override
    public void afterPropertiesSet() {
        if (speciesRepository.count() == 0) {
            speciesRepository.saveAll(initSpecies);
        }
    }
}
