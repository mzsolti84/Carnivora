package hu.progmatic.klad;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

    public class TesztFileTeljesNev {

        public static String getTeljesNev(String resourceNev) throws URISyntaxException {
            URL resource = TesztFileTeljesNev.class.getClassLoader().getResource(resourceNev);
            assertNotNull(resource);
            return Paths.get(resource.toURI()).toFile().getAbsolutePath();
        }
}
