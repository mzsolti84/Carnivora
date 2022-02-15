package hu.progmatic.carnivora;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KladForGsonDto {
    private Integer key;
    private String name;
    private String latinNev;
    private Integer parent;
}
