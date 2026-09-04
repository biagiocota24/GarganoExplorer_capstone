package biagioCota.biagio.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CommentoPayload {

    @NotNull(message = "L'ID autore è obbligatorio")
    private UUID autoreId;

    @NotNull(message = "L'ID post è obbligatorio")
    private Long postId;

    @NotBlank(message = "Il contenuto è obbligatorio")
    @Size(max = 500, message = "Il contenuto non può superare 500 caratteri")
    private String contenuto;
}
