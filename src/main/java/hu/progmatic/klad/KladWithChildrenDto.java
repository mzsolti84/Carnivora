package hu.progmatic.klad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class KladWithChildrenDto implements Serializable {
    private Integer id;
    private String name;
    private String latinName;
    private String description;
    private String parent;
    private List<KladWithChildrenDto> children;

}
