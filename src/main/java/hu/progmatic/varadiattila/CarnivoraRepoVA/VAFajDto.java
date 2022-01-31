package hu.progmatic.varadiattila.CarnivoraRepoVA;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class VAFajDto implements Serializable {
    private final Integer id;
    private final String name;
    private final String szuloNev;
}
