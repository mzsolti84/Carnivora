package hu.progmatic.carnivora;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class KladWithChildrenDto implements Serializable {
    private Integer id;
    private String nev;
    private String latinNev;
    private String leiras;
    private String szulo;
    private Integer kepId;
    private List<KladWithChildrenDto> leszarmazott;

}
