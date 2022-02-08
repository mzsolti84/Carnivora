package hu.progmatic.databaseinit;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class GetWholePathOfResource {

    public static String getWholePath(String resourceName) {
        try{
            URL resource = GetWholePathOfResource.class.getClassLoader().getResource(String.format("databaseinit/%s", resourceName));
            if (resource == null) {
                throw new CsvURISyntaxException("URL resource is null");
            }
            return Paths.get(resource.toURI()).toFile().getAbsolutePath();
        }catch (URISyntaxException e){
            throw new CsvURISyntaxException("URISyntax error");
        }
    }
}
