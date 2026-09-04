package biagioCota.biagio.payloads.strutture;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IndirizzoPayload {

    @NotBlank(message = "La via è obbligatoria")
    @Size(max = 260, message = "La via non può superare 260 caratteri")
    private String via;

    @NotBlank(message = "Il numero civico è obbligatorio")
    @Size(max = 10, message = "Il numero civico non può superare 10 caratteri")
    private String numeroCivico;
}
