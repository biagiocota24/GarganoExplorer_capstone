package biagioCota.biagio.payloads.users;

import biagioCota.biagio.security.validator.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBasePayload {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve essere tra 2 e 50 caratteri")
    private String name;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il cognome deve essere tra 2 e 50 caratteri")
    private String surname;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;

    @NotBlank(message = "La password è obbligatoria")
    @ValidPassword
    private String password;

    @NotBlank(message = "La biografia è obbligatoria")
    @Size(max = 500, message = "La biografia non può superare 500 caratteri")
    private String biografy;

    @NotBlank(message = "Il telefono è obbligatorio")
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "Numero di telefono non valido")
    private String telephone;

    @Size(max = 500, message = "L'URL avatar non può superare 500 caratteri")
    private String avatar;
}
