package biagioCota.biagio.payloads.users;

import biagioCota.biagio.enums.Nazionalita;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdatePayload {

    @Size(min = 2, max = 50, message = "Il nome deve essere tra 2 e 50 caratteri")
    private String name;

    @Size(min = 2, max = 50, message = "Il cognome deve essere tra 2 e 50 caratteri")
    private String surname;

    @Size(max = 500, message = "La biografia non può superare 500 caratteri")
    private String biografy;

    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "Numero di telefono non valido")
    private String telephone;

    @Size(max = 500, message = "L'URL avatar non può superare 500 caratteri")
    private String avatar;

    private Nazionalita nazionalita;
}
