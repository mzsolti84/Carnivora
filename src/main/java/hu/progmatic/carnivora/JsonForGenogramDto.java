package hu.progmatic.carnivora;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class JsonForGenogramDto {
    private String json;
}
