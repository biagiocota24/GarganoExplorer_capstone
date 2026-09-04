package biagioCota.biagio.payloads.strutture;

import biagioCota.biagio.enums.DayOfWeek;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class OrarioAperturaPayload {

    @NotNull(message = "Il giorno è obbligatorio")
    private DayOfWeek giorno;

    private LocalTime apertura;

    private LocalTime chiusura;

    @NotNull(message = "Indicare se il locale è chiuso")
    private Boolean chiuso;

    @Min(0) @Max(6)
    private int ordine;
}
