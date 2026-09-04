package biagioCota.biagio.payloads.strutture;

import biagioCota.biagio.enums.TipoSpiaggia;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StabilimentoBalnearePayload extends StrutturaBasePayload {

    @NotNull(message = "Il tipo di spiaggia è obbligatorio")
    private TipoSpiaggia tipoSpiaggia;

    @Positive(message = "Il prezzo dell'ombrellone deve essere positivo")
    private double prezzoOmbrellone;

    @NotNull(message = "Specificare se è presente la doccia")
    private Boolean docciaPresente;

    @NotNull(message = "Specificare se è presente il bar")
    private Boolean barPresente;

    @NotNull(message = "Specificare se è presente la ristorazione")
    private Boolean ristorazionePresente;
}
