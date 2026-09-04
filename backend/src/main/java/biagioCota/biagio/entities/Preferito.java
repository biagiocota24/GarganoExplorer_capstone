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
public class Preferito {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Visitor autore;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private Struttura struttura;

    @Column(nullable = false)
    private LocalDateTime dataSalvataggio;

    @Column(nullable = true, length = 500)
    private String nota;

    @Column(nullable = false)
    private Boolean visitato;
}
