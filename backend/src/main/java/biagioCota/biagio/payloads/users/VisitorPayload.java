package biagioCota.biagio.payloads.users;

import biagioCota.biagio.enums.Nazionalita;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VisitorPayload extends UserBasePayload {

    @NotNull(message = "La nazionalità è obbligatoria")
    private Nazionalita nazionalita;
}
