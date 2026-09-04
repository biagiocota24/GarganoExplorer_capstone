package biagioCota.biagio.services;

import biagioCota.biagio.entities.*;
import biagioCota.biagio.entities.strutturaSubclasses.*;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.enums.TipologiaStruttura;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.payloads.strutture.OrarioAperturaPayload;
import biagioCota.biagio.payloads.strutture.StrutturaCreatePayload;
import biagioCota.biagio.repositories.StrutturaRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StrutturaService {

    private final StrutturaRepository strutturaRepository;
    private final BusinessOwnerService businessOwnerService;
    private final CittaService cittaService;

    public StrutturaService(StrutturaRepository strutturaRepository,
                            @Lazy BusinessOwnerService businessOwnerService,
                            CittaService cittaService) {
        this.strutturaRepository = strutturaRepository;
        this.businessOwnerService = businessOwnerService;
        this.cittaService = cittaService;
    }

    @Transactional(readOnly = true)
    public List<Struttura> findAll() {
        return strutturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Struttura> findAll(Pageable pageable) {
        return strutturaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Struttura> findByTipologia(TipologiaStruttura tipologia, Pageable pageable) {
        return strutturaRepository.findByTipologia(tipologia, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Struttura> findByCittaId(UUID cittaId, Pageable pageable) {
        return strutturaRepository.findByCittàId(cittaId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Struttura> findByTipologiaAndCittaId(TipologiaStruttura tipologia, UUID cittaId, Pageable pageable) {
        return strutturaRepository.findByTipologiaAndCittàId(tipologia, cittaId, pageable);
    }

    @Transactional(readOnly = true)
    public Struttura findById(UUID id) {
        return strutturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Struttura non trovata con id: " + id));
    }

    @Transactional
    public Struttura save(Struttura struttura) {
        return strutturaRepository.save(struttura);
    }

    @Transactional
    public void delete(UUID id) {
        findById(id);
        strutturaRepository.deleteById(id);
    }

    @Transactional
    public void deleteIfOwner(UUID id, String email) {
        Struttura struttura = findById(id);
        if (struttura.getBusinessOwner() == null ||
                !struttura.getBusinessOwner().getEmail().equals(email)) {
            throw new AccessDeniedException("Non sei il proprietario di questa struttura");
        }
        strutturaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Struttura> findByTipologia(TipologiaStruttura tipologia) {
        return strutturaRepository.findByTipologia(tipologia);
    }

    @Transactional(readOnly = true)
    public List<Struttura> findByCitta(Citta citta) {
        return strutturaRepository.findByCittà(citta);
    }

    @Transactional(readOnly = true)
    public List<Struttura> findByCittaId(UUID cittaId) {
        return strutturaRepository.findByCittàId(cittaId);
    }

    @Transactional(readOnly = true)
    public List<Struttura> searchByName(String name) {
        return strutturaRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public List<Struttura> findByBusinessOwnerId(UUID businessOwnerId) {
        return strutturaRepository.findByBusinessOwnerId(businessOwnerId);
    }

    @Transactional(readOnly = true)
    public List<Struttura> findByBusinessOwnerEmail(String email) {
        BusinessOwner owner = businessOwnerService.findByEmail(email);
        return strutturaRepository.findByBusinessOwnerId(owner.getId());
    }

    public boolean existsByNameAndCitta(String name, Citta citta) {
        return strutturaRepository.existsByNameAndCittà(name, citta);
    }

    @Transactional
    public Struttura createFromEmail(StrutturaCreatePayload payload, String email) {
        BusinessOwner owner = businessOwnerService.findByEmail(email);
        Citta citta = cittaService.findById(payload.getCittaId());

        Struttura struttura = instantiateSubclass(payload.getTipologia());
        applyBaseFields(struttura, payload, citta, owner);
        applyTypeSpecificFields(struttura, payload);

        return strutturaRepository.save(struttura);
    }

    @Transactional
    public Struttura updateFromEmail(UUID id, StrutturaCreatePayload payload, String email) {
        Struttura struttura = findById(id);

        if (struttura.getBusinessOwner() == null ||
                !struttura.getBusinessOwner().getEmail().equals(email)) {
            throw new AccessDeniedException("Non sei il proprietario di questa struttura");
        }

        Citta citta = cittaService.findById(payload.getCittaId());
        applyBaseFields(struttura, payload, citta, struttura.getBusinessOwner());
        applyTypeSpecificFields(struttura, payload);

        return strutturaRepository.save(struttura);
    }

    @Transactional
    public Struttura updateAsAdmin(UUID id, StrutturaCreatePayload payload) {
        Struttura struttura = findById(id);
        Citta citta = cittaService.findById(payload.getCittaId());
        applyBaseFields(struttura, payload, citta, struttura.getBusinessOwner());
        applyTypeSpecificFields(struttura, payload);
        return strutturaRepository.save(struttura);
    }

    private void applyBaseFields(Struttura struttura, StrutturaCreatePayload p, Citta citta, BusinessOwner owner) {
        struttura.setName(p.getName());
        struttura.setDescrizione(p.getDescrizione());
        struttura.setTipologia(p.getTipologia());
        struttura.setCittà(citta);
        struttura.setTelefono(p.getTelefono());
        struttura.setEmail(p.getEmail());
        struttura.setSitoWebURL(p.getSitoWebURL());
        struttura.setAccessoDisabili(p.getAccessoDisabili());
        struttura.setAttributiAggiuntivi(p.getAttributiAggiuntivi());
        struttura.setBusinessOwner(owner);

        if (p.getFotoUrls() != null) {
            struttura.getRaccoltaFoto().clear();
            struttura.getRaccoltaFoto().addAll(p.getFotoUrls());
        }

        if (p.getIndirizzo() != null) {
            struttura.setIndirizzo(new Indirizzo(
                    p.getIndirizzo().getVia(),
                    p.getIndirizzo().getNumeroCivico()
            ));
        }

        if (p.getOrariApertura() != null) {
            List<OrarioApertura> orari = p.getOrariApertura().stream()
                    .map(o -> new OrarioApertura(o.getGiorno(), o.getApertura(), o.getChiusura(), o.getChiuso(), o.getOrdine()))
                    .collect(Collectors.toList());
            struttura.getOrariApertura().clear();
            struttura.getOrariApertura().addAll(orari);
        }
    }

    private void applyTypeSpecificFields(Struttura struttura, StrutturaCreatePayload p) {
        switch (struttura) {
            case Hotel h -> {
                if (p.getStelle() != null) h.setStelle(p.getStelle());
                if (p.getPrezzoMedioNotte() != null) h.setPrezzoMedioNotte(p.getPrezzoMedioNotte());
                if (p.getWifi() != null) h.setWifi(p.getWifi());
                if (p.getParcheggioPrivato() != null) h.setParcheggioPrivato(p.getParcheggioPrivato());
                if (p.getPiscina() != null) h.setPiscina(p.getPiscina());
                if (p.getAnimaliAmmessi() != null) h.setAnimaliAmmessi(p.getAnimaliAmmessi());
            }
            case Ristorante r -> {
                if (p.getSpecialita() != null) r.setSpecialita(p.getSpecialita());
                if (p.getPrezzoMin() != null && p.getPrezzoMax() != null)
                    r.setFasciaPrezzoMedio(new RangePrezzo(p.getPrezzoMin(), p.getPrezzoMax()));
                if (p.getTipologiaCucina() != null) r.setTipologiaCucina(p.getTipologiaCucina());
                if (p.getPrenotazioniOnline() != null) r.setPrenotazioniOnline(p.getPrenotazioniOnline());
                if (p.getDelivery() != null) r.setDelivery(p.getDelivery());
            }
            case Negozio n -> {
                if (p.getTipiMerce() != null) n.setTipiMerce(p.getTipiMerce());
                if (p.getSpedizioni() != null) n.setSpedizioni(p.getSpedizioni());
            }
            case AttrazionePrincipale a -> {
                if (p.getTipoAttrazione() != null) a.setTipoAttrazione(p.getTipoAttrazione());
                if (p.getBigliettoEntrata() != null) a.setBigliettoEntrata(p.getBigliettoEntrata());
            }
            case StabilimentoBalneare sb -> {
                if (p.getTipoSpiaggia() != null) sb.setTipoSpiaggia(p.getTipoSpiaggia());
                if (p.getPrezzoOmbrellone() != null) sb.setPrezzoOmbrellone(p.getPrezzoOmbrellone());
                if (p.getDocciaPresente() != null) sb.setDocciaPresente(p.getDocciaPresente());
                if (p.getBarPresente() != null) sb.setBarPresente(p.getBarPresente());
                if (p.getRistorazionePresente() != null) sb.setRistorazionePresente(p.getRistorazionePresente());
            }
            case Servizio sv -> {
                if (p.getTipoServizio() != null) sv.setTipoServizio(p.getTipoServizio());
                if (p.getH24() != null) sv.setH24(p.getH24());
            }
            case Trasporto t -> {
                if (p.getTipoTrasporto() != null) t.setTipoTrasporto(p.getTipoTrasporto());
                if (p.getPagamentoDigitale() != null) t.setPagamentoDigitale(p.getPagamentoDigitale());
            }
            default -> { /* struttura base, nessun campo extra */ }
        }
    }

    private Struttura instantiateSubclass(TipologiaStruttura tipologia) {
        return switch (tipologia) {
            case HOTEL -> new Hotel();
            case RISTORANTE, BAR_CAFFE, AGRITURISMO -> new Ristorante();
            case NEGOZIO -> new Negozio();
            case SPIAGGIA, STABILIMENTO_BALNEARE -> new StabilimentoBalneare();
            case TRASPORTO -> new Trasporto();
            case MUSEO, AREA_NATURALE, ATTIVITA_ACQUA -> new AttrazionePrincipale();
        };
    }
}
