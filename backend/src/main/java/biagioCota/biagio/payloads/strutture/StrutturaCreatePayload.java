package biagioCota.biagio.payloads.strutture;

import biagioCota.biagio.enums.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StrutturaCreatePayload extends StrutturaBasePayload {

    // Hotel
    private Integer stelle;
    private Double prezzoMedioNotte;
    private Boolean wifi;
    private Boolean parcheggioPrivato;
    private Boolean piscina;
    private Boolean animaliAmmessi;

    // Ristorante / Bar / Agriturismo
    private String specialita;
    private BigDecimal prezzoMin;
    private BigDecimal prezzoMax;
    private TipologiaCucina tipologiaCucina;
    private Boolean prenotazioniOnline;
    private Boolean delivery;

    // Negozio
    private List<TipoMerce> tipiMerce;
    private Boolean spedizioni;

    // Attrazione principale / Museo / Area naturale / Attività acqua
    private TipoAttrazione tipoAttrazione;
    private Double bigliettoEntrata;

    // Stabilimento balneare / Spiaggia
    private TipoSpiaggia tipoSpiaggia;
    private Double prezzoOmbrellone;
    private Boolean docciaPresente;
    private Boolean barPresente;
    private Boolean ristorazionePresente;

    // Servizio
    private TipoServizio tipoServizio;
    private Boolean h24;

    // Trasporto
    private TipoTrasporto tipoTrasporto;
    private Boolean pagamentoDigitale;
}
