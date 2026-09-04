package biagioCota.biagio.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Indirizzo {

    @Column(name = "via", length = 260)
    private String via;

    @Column(name = "numero_civico", length = 10)
    private String numeroCivico;
}
