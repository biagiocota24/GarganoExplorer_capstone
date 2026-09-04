package biagioCota.biagio.payloads.users;

import biagioCota.biagio.entities.User;
import biagioCota.biagio.entities.userSubclasses.Admin;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.enums.Nazionalita;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private UUID id;
    private String name;
    private String surname;
    private String email;
    private String biografy;
    private String telephone;
    private String avatar;
    private String role;
    private LocalDateTime entryDate;
    private boolean active;

    private Nazionalita nazionalita;
    private LocalDate dataAssunzione;

    public static UserResponse fromEntity(User user) {
        UserResponse r = new UserResponse();
        r.id = user.getId();
        r.name = user.getName();
        r.surname = user.getSurname();
        r.email = user.getEmail();
        r.biografy = user.getBiografy();
        r.telephone = user.getTelephone();
        r.avatar = user.getAvatar();
        r.entryDate = user.getEntryDate();
        r.active = user.isActive();

        if (user instanceof Visitor v) {
            r.role = "VISITOR";
            r.nazionalita = v.getNazionalita();
        } else if (user instanceof Admin a) {
            r.role = "ADMIN";
            r.dataAssunzione = a.getDataAssunzione();
        } else if (user instanceof BusinessOwner) {
            r.role = "BUSINESS_OWNER";
        }

        return r;
    }
}
