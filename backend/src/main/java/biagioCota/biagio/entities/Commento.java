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
@NoArgsConstructor
@AllArgsConstructor
public class Commento {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false  )
    private Visitor autore;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private PostCommunity post;

    @Column(nullable = false, length = 500)
    private String contenuto;

    @Column(nullable = true)
    private LocalDateTime dataCreazione;

    @Column(nullable = false)
    private Integer miPiace;
}
