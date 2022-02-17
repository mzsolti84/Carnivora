package hu.progmatic.carnivora;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Getter
@Setter
public class KozosOsDto {

    @Builder.Default
    List<FajDto> fajDtoList = new ArrayList<>();
    Integer valasztas1;
    Integer valasztas2;
}
