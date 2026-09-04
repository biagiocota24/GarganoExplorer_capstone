package biagioCota.biagio.entities.strutturaSubclasses;

import biagioCota.biagio.entities.Struttura;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("Hotel")
@Getter
@Setter
@NoArgsConstructor
public class Hotel extends Struttura {

    @Column(nullable = true)
    private Integer stelle;

    @Column(nullable = true)
    private Double prezzoMedioNotte;

    @Column(nullable = true)
    private Boolean wifi;

    @Column(nullable = true)
    private Boolean parcheggioPrivato;

    @Column(nullable = true)
    private Boolean piscina;

    @Column(nullable = true)
    private Boolean animaliAmmessi;

}
