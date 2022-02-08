package hu.progmatic.carnivora;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.progmatic.databaseinit.CsvURISyntaxException;
import hu.progmatic.databaseinit.GetWholePathOfResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        public Map<String, Object> jsonToMap() {
            // create variable loc that store location of the Sample.json file
            String loc;
            Map<String, Object> aboutData = new HashMap<>();
            try {
                loc = GetWholePathOfResource.getWholePath("about.json");
            } catch (URISyntaxException e) {
                throw new CsvURISyntaxException(e.getMessage());
            }
            String result;
            try {
                // read byte data from the Sample.json file and convert it into String
                result = new String(Files.readAllBytes(Paths.get(loc)));
                // store string data into Map by using TypeToken class
                aboutData = new Gson().fromJson(result, new TypeToken<HashMap<String, Object>>() {
                }.getType());
                // print all key-value pairs
                System.out.println("Key : " + aboutData.get("Cel"));

            } catch (IOException e1) {

            }
            return aboutData;
        }

}