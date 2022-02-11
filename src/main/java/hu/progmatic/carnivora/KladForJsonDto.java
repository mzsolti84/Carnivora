package hu.progmatic.carnivora;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KladForJsonDto {
    private Integer key;
    private String name;
    private String latinNev;
    private Integer parent;
}
