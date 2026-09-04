package biagioCota.biagio.payloads;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PreferitoAddPayload {

    @Size(max = 500, message = "La nota non può superare 500 caratteri")
    private String nota;

    private Boolean visitato = false;
}
