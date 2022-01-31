package hu.progmatic.carnivoraProject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KladBevitelForm {
    @NotNull
    Integer szuloId;
    @NotEmpty
    String megnevezes;
}
