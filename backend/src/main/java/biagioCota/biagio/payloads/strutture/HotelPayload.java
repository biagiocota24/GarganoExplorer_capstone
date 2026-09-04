package biagioCota.biagio.payloads.strutture;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HotelPayload extends StrutturaBasePayload {

    @Min(value = 1, message = "Le stelle devono essere almeno 1")
    @Max(value = 5, message = "Le stelle non possono superare 5")
    private int stelle;

    @PositiveOrZero(message = "Il prezzo medio a notte non può essere negativo")
    private double prezzoMedioNotte;

    @NotNull(message = "Specificare se è presente il wifi")
    private Boolean wifi;

    @NotNull(message = "Specificare se è presente il parcheggio privato")
    private Boolean parcheggioPrivato;

    @NotNull(message = "Specificare se è presente la piscina")
    private Boolean piscina;

    @NotNull(message = "Specificare se gli animali sono ammessi")
    private Boolean animaliAmmessi;
}
