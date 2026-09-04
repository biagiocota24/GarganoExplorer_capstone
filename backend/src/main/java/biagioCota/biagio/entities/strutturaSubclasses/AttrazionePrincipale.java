package biagioCota.biagio.entities.strutturaSubclasses;

import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.enums.TipoAttrazione;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Attrazione principale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttrazionePrincipale extends Struttura {

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TipoAttrazione tipoAttrazione;

    @Column(nullable = true)
    private Double bigliettoEntrata;
}