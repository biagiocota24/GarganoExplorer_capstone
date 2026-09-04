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
public class PostCommunityPayload {

    @NotNull(message = "L'ID autore è obbligatorio")
    private UUID autoreId;

    @NotNull(message = "L'ID città è obbligatorio")
    private UUID cittaId;

    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(min = 5, max = 200, message = "Il titolo deve essere tra 5 e 200 caratteri")
    private String titolo;

    @NotBlank(message = "Il contenuto è obbligatorio")
    @Size(min = 10, max = 5000, message = "Il contenuto deve essere tra 10 e 5000 caratteri")
    private String contenuto;
}
