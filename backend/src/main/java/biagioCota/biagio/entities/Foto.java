package biagioCota.biagio.entities;

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
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "struttura_id", nullable = false)
    private Struttura struttura;

    @Column(nullable = false)
    private String urlFoto;

    private Integer posizione;

    @Column(name = "data_caricamento")
    private LocalDateTime dataCaricamento = LocalDateTime.now();

    @Column(name = "caricata_da_user_id")
    private UUID caricataDaUserId;

    private Boolean visibile = true;

    private Integer numeroVisite = 0;
}
