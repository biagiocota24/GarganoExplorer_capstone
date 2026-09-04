package biagioCota.biagio.entities.userSubclasses;

import biagioCota.biagio.entities.*;
import biagioCota.biagio.enums.Nazionalita;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Visitor extends User {

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Nazionalita nazionalita;

    @OneToMany(mappedBy = "autore")
    private List<Valutazione> valutazioni;

    @OneToMany(mappedBy = "autore")
    private List<Commento> commenti;

    @OneToMany(mappedBy = "autore")
    private List<Preferito> preferiti;

    @OneToMany(mappedBy = "autore")
    private List<Messaggio> messaggiBlog;

    public Visitor(String name, String surname, String email, String password, String biografy, String telephone, String avatar, Nazionalita nazionalita) {
        super(name, surname, email, password, biografy, telephone, avatar);
        this.nazionalita = nazionalita;
        this.valutazioni = new ArrayList<>();
        this.commenti = new ArrayList<>();
        this.preferiti = new ArrayList<>();
        this.messaggiBlog = new ArrayList<>();
    }
}
