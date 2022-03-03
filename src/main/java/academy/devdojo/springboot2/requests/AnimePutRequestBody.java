package academy.devdojo.springboot2.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AnimePutRequestBody {
    @NotNull(message = "ID não pode ser nulo")
    private Long id;
    @NotEmpty(message = "Nome não pode ser nulo")
    private String name;
}
