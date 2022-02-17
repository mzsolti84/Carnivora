package hu.progmatic.carnivora;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KladDto {
    private Integer id;
    private String nev;
    private String latinNev;
    private String leiras;
    private Integer szuloId;
    private String szuloNev;

    private Integer kepId;
}
