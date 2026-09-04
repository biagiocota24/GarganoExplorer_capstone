package biagioCota.biagio.payloads.users;

import biagioCota.biagio.entities.User;
import biagioCota.biagio.entities.userSubclasses.Admin;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.enums.Nazionalita;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserPayloadResponse {
    @NotBlank(message = "Il nome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve essere tra 2 e 50 caratteri")
    private String name;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(min = 2, max = 50, message = "Il cognome deve essere tra 2 e 50 caratteri")
    private String surname;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;

    @NotBlank(message = "La biografia è obbligatoria")
    @Size(max = 500, message = "La biografia non può superare 500 caratteri")
    private String biografy;

    @NotBlank(message = "Il telefono è obbligatorio")
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "Numero di telefono non valido")
    private String telephone;

    @Size(max = 500, message = "L'URL avatar non può superare 500 caratteri")
    private String avatar;

    private String role;

    @NotBlank
    private UUID id;

    private Nazionalita nazionalita;

    private LocalDate dataAssunzione;

    public static UserPayloadResponse fromVisitorEntity(Visitor user) {
        UserPayloadResponse response = new UserPayloadResponse();
        response.name = user.getName();
        response.surname = user.getSurname();
        response.email = user.getEmail();
        response.biografy = user.getBiografy();
        response.telephone = user.getTelephone();
        response.id = user.getId();
        response.role = "visitor";
        response.nazionalita = user.getNazionalita();
        return response;
    }

    public static UserPayloadResponse fromAdminEntity(Admin user) {
        UserPayloadResponse response = new UserPayloadResponse();
        response.name = user.getName();
        response.surname = user.getSurname();
        response.email = user.getEmail();
        response.biografy = user.getBiografy();
        response.telephone = user.getTelephone();
        response.id = user.getId();
        response.role = "admin";
        response.dataAssunzione = user.getDataAssunzione();
        return response;
    }

    public static UserPayloadResponse fromBusinessOwnerEntity(BusinessOwner user) {
        UserPayloadResponse response = new UserPayloadResponse();
        response.name = user.getName();
        response.surname = user.getSurname();
        response.email = user.getEmail();
        response.biografy = user.getBiografy();
        response.telephone = user.getTelephone();
        response.id = user.getId();
        response.role = "businessOwner";
        return response;
    }
}
