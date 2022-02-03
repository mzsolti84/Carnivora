package hu.progmatic.carnivoraProject.user;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;


    public class TesztFileTeljesNev {

        public static String getTeljesNev(String resourceNev) throws URISyntaxException {
            URL resource = TesztFileTeljesNev.class.getClassLoader().getResource(resourceNev);
            return Paths.get(resource.toURI()).toFile().getAbsolutePath();
        }
}
