package hu.progmatic.carnivora;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SzuloKladDto {

    private Integer id;
    private String nev;

}
