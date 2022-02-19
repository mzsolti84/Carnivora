package hu.progmatic.carnivora;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class NodeForBloodLineDto {
    private Integer key;
    private String name;
    private String latinNev;
    private Integer parent;

}
