package biagioCota.biagio.entities.strutturaSubclasses;

import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.enums.TipoMerce;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("Negozio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Negozio extends Struttura {

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "negozio_tipi_merce", joinColumns = @JoinColumn(name = "struttura_id"))
    @Column(name = "tipo_merce")
    private List<TipoMerce> tipiMerce = new ArrayList<>();

    @Column(nullable = true)
    private Boolean spedizioni;
}
