package biagioCota.biagio.entities;

import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.enums.TipologiaStruttura;
import biagioCota.biagio.jsonConverter.JsonAttributesConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_struttura", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@NoArgsConstructor
public abstract class Struttura {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String descrizione;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipologiaStruttura tipologia;

    @Embedded
    private Indirizzo indirizzo;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Citta città;

    @ElementCollection
    @CollectionTable(name = "struttura_foto", joinColumns = @JoinColumn(name = "struttura_id"))
    @Column(name = "url_foto")
    private List<String> raccoltaFoto = new ArrayList<>();

    @OneToMany(mappedBy = "struttura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> foto = new ArrayList<>();

    @OneToMany(mappedBy = "struttura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Preferito> preferito = new ArrayList<>();

    @OneToMany(mappedBy = "struttura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Valutazione> valutazione = new ArrayList<>();

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = true)
    private String sitoWebURL;

    @Column(columnDefinition = "text", nullable = true)
    @Convert(converter = JsonAttributesConverter.class)
    private Map<String, Object> attributiAggiuntivi = new HashMap<>();

    @Column(nullable = false)
    private Boolean accessoDisabili;

    @ElementCollection
    @CollectionTable(name = "struttura_orari", joinColumns = @JoinColumn(name = "struttura_id"))
    private List<OrarioApertura> orariApertura = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "business_owner_id")
    private BusinessOwner businessOwner;

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private LocalDate dataRegistrazione = LocalDate.now();
}
