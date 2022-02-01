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
                    "Madagaszkár legnagyobb ragadozó állata; a nőstény testhossza 65–70 centiméter, " +
                            "a hímé 75–80 centiméter, farokhossza 70–90 centiméter, a nőstény testtömege 5–7 kilogramm, " +
                            "a hímé 6–10 kilogramm. Külsőleg hasonlít a macskákhoz, afféle átmenet a macskák és cibetmacskák között. " +
                            "Különösen szembeötlővé válik ez a macskaszerűség, ha a fosszát a vele egyező színezetű, megnyúlt alakú, " +
                            "alacsony tairával (Eira barbara) vetjük össze. A hasonlóság miatt a rendszertani helye is hosszú ideig vitás volt.",
                    "Fossza",
                    "Cryptoprocta ferox",
                    VeszelyeztetettKategoriak.SEBEZHETO,
                    Tureshatar.SPECIALISTA,
                    "https://upload.wikimedia.org/wikipedia/commons/8/8b/Cryptoprocta_ferox_-_1700-1880_-_Print_-_Iconographia_Zoologica_-_Special_Collections_University_of_Amsterdam_-%28white_background%29.png",
                    null),
            new FajRecord(null,
                    6,
                    "macskaformák alcsaládja",
                    """
                    A macskák élvezik a meleget és a napsütést, gyakran alszanak napos helyeken. Magasabb hőmérsékleten érzik jól magukat, 
                    mint az emberek, akik számára a 44,5 °C-nál nagyobb bőrfelszíni hőmérséklet már nehezen elviselhető, míg a macskák akkor 
                    kezdik kellemetlenül érezni magukat, ha a bőrük 52 °C-ra melegszik. Körülbelül 10 000 évvel ezelőtt kezdett az ember társaságában élni, 
                    háziasításának első ábrázolása mintegy 4000 éve Egyiptomban történt. Számos kínai rajz bizonyítja, hogy a Távol-Keleten már i. e. 1500 
                    körül ismerték a macskát, mely az Egyiptommal ősidők óta kapcsolatban álló Indiából kerülhetett oda, ahol vallási ceremóniáknál kapott 
                    fontos szerepet. Kezdetben a gazdagok státusszimbóluma volt, majd a későbbiekben az értékes selyem kártevőktől való megvédéséhez használták fel. 
                    Japánban a szent iratok megóvásában nyújtott segítséget.""",
                    "Házimacska",
                    "Felis silvestris catus",
                    VeszelyeztetettKategoriak.HAZIASITOTT,
                    Tureshatar.SPECIALISTA,
                    "https://upload.wikimedia.org/wikipedia/commons/e/e7/Jammlich_crop.jpg",
                    "https://hu.wikipedia.org/wiki/Macska"),
            new FajRecord(null,
                    4,
                    "madagaszkári cibetmacskafélék családja",
                    "Madagaszkáron honos. Kevés megfigyelés van róla. Megeszi a gilisztát, a csigát és a rovarokat is.",
                    "Falanuk",
                    "Eupleres goudotii",
                    VeszelyeztetettKategoriak.FENYEGETETT,
                    Tureshatar.GENERALISTA,
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0e/EupleresGoudotiSmit.jpg/2560px-EupleresGoudotiSmit.jpg",
                    "https://hu.wikipedia.org/wiki/Falanuk"),

            new FajRecord(null,
                    15,
                    "kutyafélék családja",
                    """
                            Holasztikus faj (az ember után) a legnagyobb területen elterjedt emlősök egyike. 
                            Észak-Amerika és Eurázsia északi területeinek meghatározó csúcsragadozója, de megtalálhatjuk képviselőit 
                            Közép-Amerikában, Észak-Afrikában és Dél-Ázsiában is. Az egykor összefüggő, hatalmas területen a túlzott vadászat 
                            következtében ma már csak elszigetelt csoportjai maradtak fenn, így például Észak-Afrika szavanna övezetéből 
                            az ember teljesen kiszorította. Kihalt Japánban is; az Arab-félszigeten erősen veszélyeztetett, szinte el is tűnt. 
                            Természetes élőhelyét nehéz meghatározni, hiszen (az ember kivételével) a farkas a különböző biomokhoz leginkább alkalmazkodott emlős: 
                            a félsivatagtól a tundrán át a trópusi esőerdőig mindenhol képes megélni.""",
                    "Szürke farkas",
                    "Canis lupus",
                    VeszelyeztetettKategoriak.FENYEGETETT,
                    Tureshatar.GENERALISTA,
                    "https://upload.wikimedia.org/wikipedia/commons/5/5f/Kolm%C3%A5rden_Wolf.jpg",
                    "https://hu.wikipedia.org/wiki/Sz%C3%BCrke_farkas")

    );

    @Override
    public void afterPropertiesSet() {
        if (speciesRepository.findAll().isEmpty()) {
            speciesRepository.saveAll(initSpecies);
        }
    }

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

    public void deleteByIdIfExists(Integer id) {
        if (speciesRepository.existsById(id)) {
            FajRecord faj = speciesRepository.getById(id);
            speciesRepository.delete(faj);
        }
    }

    public FajRecord empty() {
        return new FajRecord();
    }

}
