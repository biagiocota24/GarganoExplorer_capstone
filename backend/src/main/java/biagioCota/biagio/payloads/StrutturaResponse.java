package biagioCota.biagio.payloads;

import biagioCota.biagio.entities.OrarioApertura;
import biagioCota.biagio.entities.RangePrezzo;
import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.entities.strutturaSubclasses.*;
import biagioCota.biagio.enums.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StrutturaResponse {

    private UUID id;
    private String name;
    private String descrizione;
    private TipologiaStruttura tipologia;
    private UUID cittaId;
    private String cittaNome;
    private String via;
    private String numeroCivico;
    private String telefono;
    private String email;
    private String sitoWebURL;
    private Boolean accessoDisabili;
    private Map<String, Object> attributiAggiuntivi;
    private List<String> fotoUrls;
    private LocalDate dataRegistrazione;
    //ATTRAZIONE PRINCIPALE
    private TipoAttrazione tipoAttrazione;
    private Double bigliettoEntrata;
    //HOTEL
    private Integer stelle;
    private Double prezzoMedioNotte;
    private Boolean wifi;
    private Boolean parcheggioPrivato;
    private Boolean piscina;
    private Boolean animaliAmmessi;
    // NEGOZIO
    private List<TipoMerce> tipiMerce = new ArrayList<>();
    private Boolean spedizioni;
    //RISTORANTE
    private String specialita;
    private RangePrezzo fasciaPrezzoMedio;
    private TipologiaCucina tipologiaCucina;
    private Boolean prenotazioniOnline;
    private Boolean delivery;
    //SERVIZIO
    private TipoServizio tipoServizio;
    private Boolean h24;
    //STABILIMENTO BALNEARE
    private TipoSpiaggia tipoSpiaggia;
    private Double prezzoOmbrellone;
    private Boolean docciaPresente;
    private Boolean barPresente;
    private Boolean ristorazionePresente;
    //TRASPORTO
    private TipoTrasporto tipoTrasporto;
    private Boolean pagamentoDigitale;
    // ORARI
    private List<OrarioApertura> orariApertura = new ArrayList<>();

    public static StrutturaResponse fromEntity(Struttura s) {
        StrutturaResponse r = new StrutturaResponse();
        r.id = s.getId();
        r.name = s.getName();
        r.descrizione = s.getDescrizione();
        r.tipologia = s.getTipologia();
        r.telefono = s.getTelefono();
        r.email = s.getEmail();
        r.sitoWebURL = s.getSitoWebURL();
        r.fotoUrls=s.getRaccoltaFoto();
        r.attributiAggiuntivi = s.getAttributiAggiuntivi();
        r.accessoDisabili = s.getAccessoDisabili();

        if (s instanceof AttrazionePrincipale) {
            r.tipoAttrazione = ((AttrazionePrincipale) s).getTipoAttrazione();
            r.bigliettoEntrata = ((AttrazionePrincipale) s).getBigliettoEntrata();
        }
        if (s instanceof Hotel) {
            r.stelle = ((Hotel) s).getStelle();
            r.prezzoMedioNotte = ((Hotel) s).getPrezzoMedioNotte();
            r.wifi = ((Hotel) s).getWifi();
            r.parcheggioPrivato = ((Hotel) s).getParcheggioPrivato();
            r.piscina = ((Hotel) s).getPiscina();
            r.animaliAmmessi = ((Hotel) s).getAnimaliAmmessi();
        }
        if (s instanceof Negozio) {
            r.tipiMerce = ((Negozio) s).getTipiMerce();
            r.spedizioni = ((Negozio) s).getSpedizioni();
        }
        if (s instanceof Ristorante) {
            r.specialita = ((Ristorante) s).getSpecialita();
            r.fasciaPrezzoMedio = ((Ristorante) s).getFasciaPrezzoMedio();
            r.tipologiaCucina = ((Ristorante) s).getTipologiaCucina();
            r.prenotazioniOnline = ((Ristorante) s).getPrenotazioniOnline();
            r.delivery = ((Ristorante) s).getDelivery();
        }
        if (s instanceof Servizio) {
            r.tipoServizio = ((Servizio) s).getTipoServizio();
            r.h24 = ((Servizio) s).getH24();
        }
        if (s instanceof StabilimentoBalneare) {
            r.tipoSpiaggia = ((StabilimentoBalneare) s).getTipoSpiaggia();
            r.prezzoOmbrellone = ((StabilimentoBalneare) s).getPrezzoOmbrellone();
            r.docciaPresente = ((StabilimentoBalneare) s).getDocciaPresente();
            r.barPresente = ((StabilimentoBalneare) s).getBarPresente();
            r.ristorazionePresente = ((StabilimentoBalneare) s).getRistorazionePresente();
        }
        if (s instanceof Trasporto) {
            r.tipoTrasporto = ((Trasporto) s).getTipoTrasporto();
            r.pagamentoDigitale = ((Trasporto) s).getPagamentoDigitale();
        }

        if (s.getCittà() != null) {
            r.cittaId = s.getCittà().getId();
            r.cittaNome = s.getCittà().getName();
        }

        if (s.getIndirizzo() != null) {
            r.via = s.getIndirizzo().getVia();
            r.numeroCivico = s.getIndirizzo().getNumeroCivico();
        }

        r.orariApertura = s.getOrariApertura();
        r.dataRegistrazione = s.getDataRegistrazione();

        return r;
    }
}
