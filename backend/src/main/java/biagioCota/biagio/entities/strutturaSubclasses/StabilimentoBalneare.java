package biagioCota.biagio.entities.strutturaSubclasses;

import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.enums.TipoSpiaggia;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Stabilimento_balneare")
@Getter
@Setter
@NoArgsConstructor
public class StabilimentoBalneare extends Struttura {

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TipoSpiaggia tipoSpiaggia;

    @Column(nullable = true)
    private Double prezzoOmbrellone;

    @Column(nullable = true)
    private Boolean docciaPresente;

    @Column(nullable = true)
    private Boolean barPresente;

    @Column(nullable = true)
    private Boolean ristorazionePresente;
}
