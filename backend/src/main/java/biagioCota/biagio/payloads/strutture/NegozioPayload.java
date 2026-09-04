package biagioCota.biagio.payloads.strutture;

import biagioCota.biagio.enums.TipoMerce;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NegozioPayload extends StrutturaBasePayload {

    @NotEmpty(message = "Specificare almeno un tipo di merce")
    private List<TipoMerce> tipoMerce;

    @NotNull(message = "Specificare se sono disponibili spedizioni")
    private Boolean spedizioni;
}
