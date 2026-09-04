package biagioCota.biagio.payloads.strutture;

import biagioCota.biagio.enums.TipoServizio;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ServizioPayload extends StrutturaBasePayload {

    @NotNull(message = "Il tipo di servizio è obbligatorio")
    private TipoServizio tipoServizio;

    @NotNull(message = "Specificare se il servizio è disponibile 24h")
    private Boolean h24;
}
