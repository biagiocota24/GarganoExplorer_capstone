package biagioCota.biagio.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CittaPayload {

    @NotBlank(message = "Il nome della città è obbligatorio")
    @Size(min = 2, max = 100, message = "Il nome deve essere tra 2 e 100 caratteri")
    private String name;

    @NotBlank(message = "Il CAP è obbligatorio")
    @Pattern(regexp = "^[0-9]{5}$", message = "Il CAP deve essere composto da 5 cifre")
    private String CAP;

    @NotBlank(message = "La provincia è obbligatoria")
    @Size(min = 2, max = 5, message = "Inserire la sigla della provincia (es. FG)")
    private String provincia;
}
