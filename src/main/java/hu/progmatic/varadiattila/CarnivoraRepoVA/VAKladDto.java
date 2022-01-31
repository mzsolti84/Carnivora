package hu.progmatic.varadiattila.CarnivoraRepoVA;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class VAKladDto implements Serializable {
    private final Integer id;
    private final String kladName;
    private final String szuloNev;
    private final List<VAFajDto> fajok;

    VAFajRepository fajRepository;
}
