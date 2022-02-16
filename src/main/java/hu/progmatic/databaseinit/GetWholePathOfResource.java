package hu.progmatic.databaseinit;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class GetWholePathOfResource {

  public static InputStream getWholePath(String resourceName) {
    return GetWholePathOfResource.class.getClassLoader()
        .getResourceAsStream(String.format("databaseinit/%s", resourceName));
  }
}
