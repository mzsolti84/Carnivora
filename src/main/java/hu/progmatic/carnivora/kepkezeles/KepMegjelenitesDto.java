package hu.progmatic.carnivora.kepkezeles;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KepMegjelenitesDto {
  private byte[] kepAdat;
  private String contentType;
}
