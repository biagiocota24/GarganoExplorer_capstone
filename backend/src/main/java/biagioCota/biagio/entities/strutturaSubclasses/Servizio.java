package biagioCota.biagio.entities.strutturaSubclasses;

import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.enums.TipoServizio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Servizio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Servizio extends Struttura {

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TipoServizio tipoServizio;

    @Column(nullable = true)
    private Boolean h24;
}
