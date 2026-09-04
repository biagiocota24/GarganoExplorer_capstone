package biagioCota.biagio.payloads.strutture;

import biagioCota.biagio.enums.TipologiaCucina;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class RistorantePayload extends StrutturaBasePayload {

    @Size(max = 300, message = "La specialità non può superare 300 caratteri")
    private String specialita;

    @NotNull(message = "Il prezzo minimo è obbligatorio")
    @PositiveOrZero(message = "Il prezzo minimo non può essere negativo")
    private BigDecimal prezzoMin;

    @NotNull(message = "Il prezzo massimo è obbligatorio")
    @Positive(message = "Il prezzo massimo deve essere positivo")
    private BigDecimal prezzoMax;

    @NotNull(message = "La tipologia cucina è obbligatoria")
    private TipologiaCucina tipologiaCucina;

    @NotNull(message = "Specificare se accetta prenotazioni online")
    private Boolean prenotazioniOnline;

    @NotNull(message = "Specificare se è disponibile il delivery")
    private Boolean delivery;
}
