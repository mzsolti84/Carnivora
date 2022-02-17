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
    private List<FajDto> fajDtoList = new ArrayList<>();
    private Integer valasztottFaj1;
    private Integer valasztottFaj2;
    private KladDto kozosOs;
}
