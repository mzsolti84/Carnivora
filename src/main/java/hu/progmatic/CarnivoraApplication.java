package hu.progmatic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarnivoraApplication {

    /**
     Ha saját gépről futtatjuk, akkor "http://localhost:8084",
     ha szerverről akkor "http://164.92.204.250"!
     */
    public static final String projectHostName = "http://localhost:8084";

    public static void main(String[] args) {
        SpringApplication.run(CarnivoraApplication.class, args);
    }

}
