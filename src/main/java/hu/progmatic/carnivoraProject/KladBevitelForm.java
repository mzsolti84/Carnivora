package hu.progmatic.carnivoraProject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KladBevitelForm {
    @NotNull
    Integer szuloId;
    @NotEmpty
    String megnevezes;
}
