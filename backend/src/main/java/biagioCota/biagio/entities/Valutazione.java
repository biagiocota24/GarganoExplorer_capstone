package biagioCota.biagio.entities;

import biagioCota.biagio.entities.userSubclasses.Visitor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Valutazione {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "autore_id")
    private Visitor autore;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "struttura_id")
    private Struttura struttura;

    @Column(nullable = false)
    private Integer stelle;

    @Column(nullable = true, length = 100)
    private String titolo;

    @Column(nullable = true, length = 1000)
    private String commento;

    @Column(nullable = false)
    private LocalDateTime dataCreazione = LocalDateTime.now();

    @Column(nullable = false)
    private Integer utileCount;


}
