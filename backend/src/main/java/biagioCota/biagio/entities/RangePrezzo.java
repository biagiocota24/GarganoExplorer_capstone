package biagioCota.biagio.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class RangePrezzo {

    @Column(name = "prezzo_minimo", precision = 10, scale = 2)
    private BigDecimal prezzoMin;

    @Column(name = "prezzo_massimo", precision = 10, scale = 2)
    private BigDecimal prezzoMax;

    public RangePrezzo(BigDecimal prezzoMin, BigDecimal prezzoMax) {
        if (prezzoMin.compareTo(prezzoMax) > 0) {
            throw new IllegalArgumentException("Prezzo minimo non può essere maggiore del prezzo massimo");
        }
        this.prezzoMin = prezzoMin;
        this.prezzoMax = prezzoMax;
    }
}
