package biagioCota.biagio.payloads;

import biagioCota.biagio.enums.TipoAzione;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class LogAzionePayload {

    @NotNull(message = "L'ID admin è obbligatorio")
    private UUID adminId;

    @NotNull(message = "Il tipo di azione è obbligatorio")
    private TipoAzione tipoAzione;

    private UUID entitaId;

    @Size(max = 50, message = "Il tipo entità non può superare 50 caratteri")
    private String tipoEntita;

    private String datiPrecedenti;

    private String datiSuccessivi;
}
