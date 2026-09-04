package biagioCota.biagio.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentoCreatePayload {

    @NotBlank(message = "Il contenuto è obbligatorio")
    @Size(max = 500, message = "Il contenuto non può superare 500 caratteri")
    private String contenuto;
}
