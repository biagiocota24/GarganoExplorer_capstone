package biagioCota.biagio.payloads;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ValutazionePayload {

    @NotNull(message = "L'ID autore è obbligatorio")
    private UUID autoreId;

    @NotNull(message = "L'ID struttura è obbligatorio")
    private UUID strutturaId;

    @NotNull(message = "Il numero di stelle è obbligatorio")
    @Min(value = 1, message = "Il voto minimo è 1 stella")
    @Max(value = 5, message = "Il voto massimo è 5 stelle")
    private Integer stelle;

    @Size(max = 100, message = "Il titolo non può superare 100 caratteri")
    private String titolo;

    @Size(max = 1000, message = "Il commento non può superare 1000 caratteri")
    private String commento;
}
