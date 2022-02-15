package hu.progmatic.carnivora.kepkezeles;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KepDto {
  private Integer id;
  private String megnevezes;
  private String meret;
}
