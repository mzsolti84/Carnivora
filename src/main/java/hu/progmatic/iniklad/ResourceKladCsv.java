package hu.progmatic.iniklad;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class ResourceKladCsv {

    public static String getWholePath(String resourceName) throws URISyntaxException {
        //assertNotNull(resource);
        URL resource = ResourceKladCsv.class.getClassLoader().getResource(String.format("carnivora/%s", resourceName));
        return Paths.get(resource.toURI()).toFile().getAbsolutePath();
        //return Paths.get("src", "main", "resources").toFile().getAbsolutePath() + String.format("/carnivora/%s", resourceName);
    }
}
