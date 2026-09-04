package biagioCota.biagio.entities.userSubclasses;

import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessOwner extends User {

    @OneToMany(mappedBy = "businessOwner")
    private List<Struttura> strutture;

    public BusinessOwner(String name, String surname, String email, String password, String biografy, String telephone, String avatar) {
        super(name, surname, email, password, biografy, telephone, avatar);
        this.strutture = new ArrayList<>();
    }
}
