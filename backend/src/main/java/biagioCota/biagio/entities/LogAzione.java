package biagioCota.biagio.entities;

import biagioCota.biagio.entities.userSubclasses.Admin;
import biagioCota.biagio.enums.StatoAzione;
import biagioCota.biagio.enums.TipoAzione;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_azioni")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogAzione {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "admin_id")
    private Admin admin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAzione tipoAzione;

    @Column(nullable = true)
    private UUID entitaId;

    @Column(nullable = true, length = 50)
    private String tipoEntita;

    @Column(nullable = false)
    private LocalDateTime dataAzione;

    @Column(nullable = true, columnDefinition = "JSON")
    private String datiPrecedenti;

    @Column(nullable = true, columnDefinition = "JSON")
    private String datiSuccessivi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoAzione stato = StatoAzione.SUCCESSO;

    @Column(nullable = true, length = 500)
    private String messaggioErrore;
}
