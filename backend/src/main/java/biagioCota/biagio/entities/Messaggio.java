package biagioCota.biagio.entities;

import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.enums.StatoMessaggio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "messaggio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Messaggio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Visitor autore;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Citta citta;

    @Column(nullable = false, length = 1000)
    private String contenuto;

    @Column(name = "foto_url", nullable = true, length = 500)
    private String fotoUrl;

    @Column(name = "data_creazione", nullable = false)
    private LocalDateTime dataCreazione = LocalDateTime.now();

    @Column(name = "data_modifica", nullable = true)
    private LocalDateTime dataModifica;

    @Column(name = "contatore_mi_piace", nullable = false)
    private Integer contatoreMiPiace = 0;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "messaggio_mi_piace",
            joinColumns = @JoinColumn(name = "messaggio_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> utentiMiPiace = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "risposta_a_id", nullable = true)
    private Messaggio rispostaA;

    @OneToMany(mappedBy = "rispostaA", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Messaggio> risposte = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoMessaggio stato = StatoMessaggio.PUBBLICATO;
}
