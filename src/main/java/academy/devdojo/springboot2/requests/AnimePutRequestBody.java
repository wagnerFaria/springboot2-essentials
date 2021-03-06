package academy.devdojo.springboot2.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePutRequestBody {
    @NotNull(message = "ID não pode ser nulo")
    private Long id;
    @NotEmpty(message = "Nome não pode ser nulo")
    private String name;
}
