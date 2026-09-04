package biagioCota.biagio.entities.strutturaSubclasses;

import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.enums.TipoTrasporto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Trasporto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trasporto extends Struttura {

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private TipoTrasporto tipoTrasporto;

    @Column(nullable = true)
    private Boolean pagamentoDigitale;
}
