package biagioCota.biagio.entities.strutturaSubclasses;

import biagioCota.biagio.entities.RangePrezzo;
import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.enums.TipologiaCucina;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Ristorante")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ristorante extends Struttura {

    @Column(nullable = true)
    private String specialita;

    @Embedded
    private RangePrezzo fasciaPrezzoMedio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TipologiaCucina tipologiaCucina;

    @Column(nullable = true)
    private Boolean prenotazioniOnline;

    @Column(nullable = true)
    private Boolean delivery;
}
