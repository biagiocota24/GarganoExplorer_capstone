package biagioCota.biagio.entities;

import biagioCota.biagio.enums.DayOfWeek;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrarioApertura {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek giorno;

    @Column(nullable = true)
    private LocalTime apertura;

    @Column(nullable = true)
    private LocalTime chiusura;

    @Column(nullable = false)
    private Boolean chiuso;

    @Column(nullable = false)
    private int ordine;
}
