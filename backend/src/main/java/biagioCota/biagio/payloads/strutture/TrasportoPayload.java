package biagioCota.biagio.payloads.strutture;

import biagioCota.biagio.enums.TipoTrasporto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrasportoPayload extends StrutturaBasePayload {

    @NotNull(message = "Il tipo di trasporto è obbligatorio")
    private TipoTrasporto tipoTrasporto;

    @NotNull(message = "Specificare se è disponibile il pagamento digitale")
    private Boolean pagamentoDigitale;
}
