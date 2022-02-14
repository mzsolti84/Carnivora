package hu.progmatic.carnivora.page;

import hu.progmatic.carnivora.Faj;
import hu.progmatic.carnivora.FajDto;
import org.springframework.data.domain.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paged<T> {
        @NotNull
        private Page<T> page;

        private Paging paging;

    }

