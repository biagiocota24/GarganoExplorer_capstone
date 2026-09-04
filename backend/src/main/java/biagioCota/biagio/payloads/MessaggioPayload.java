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
public class MessaggioPayload {

    @NotNull(message = "L'ID autore è obbligatorio")
    private UUID autoreId;

    @NotNull(message = "L'ID città è obbligatorio")
    private UUID cittaId;

    @NotBlank(message = "Il contenuto è obbligatorio")
    @Size(max = 1000, message = "Il contenuto non può superare 1000 caratteri")
    private String contenuto;

    @Size(max = 500, message = "L'URL della foto non può superare 500 caratteri")
    private String fotoUrl;

    private Long rispostaAId;
}
