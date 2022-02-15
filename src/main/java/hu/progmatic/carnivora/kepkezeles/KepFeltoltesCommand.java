package hu.progmatic.carnivora.kepkezeles;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class KepFeltoltesCommand {
  @NotEmpty
  @NotNull
  private String megnevezes;
  @NotNull
  private MultipartFile kepFile;
}
