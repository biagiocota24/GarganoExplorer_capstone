package biagioCota.biagio.payloads.strutture;

import biagioCota.biagio.enums.TipoAttrazione;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AttrazionePrincipalePayload extends StrutturaBasePayload {

    @NotNull(message = "Il tipo di attrazione è obbligatorio")
    private TipoAttrazione tipoAttrazione;

    @PositiveOrZero(message = "Il prezzo del biglietto non può essere negativo")
    private double bigliettoEntrata;
}
