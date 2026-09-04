package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Indirizzo;
import biagioCota.biagio.entities.OrarioApertura;
import biagioCota.biagio.entities.RangePrezzo;
import biagioCota.biagio.entities.strutturaSubclasses.Ristorante;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.payloads.strutture.RistorantePayload;
import biagioCota.biagio.repositories.RistoranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RistoranteService {

    private final RistoranteRepository ristoranteRepository;
    private final CittaService cittaService;
    private final BusinessOwnerService businessOwnerService;

    public RistoranteService(RistoranteRepository ristoranteRepository,
                             CittaService cittaService,
                             BusinessOwnerService businessOwnerService) {
        this.ristoranteRepository = ristoranteRepository;
        this.cittaService = cittaService;
        this.businessOwnerService = businessOwnerService;
    }

    public List<Ristorante> findAll() {
        return ristoranteRepository.findAll();
    }

    public Ristorante findById(UUID id) {
        return ristoranteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ristorante non trovato con id: " + id));
    }

    public Ristorante save(RistorantePayload payload) {
        Citta citta = cittaService.findById(payload.getCittaId());

        if (payload.getPrezzoMin().compareTo(payload.getPrezzoMax()) > 0) {
            throw new RuntimeException("Il prezzo minimo non può essere maggiore del prezzo massimo");
        }

        Ristorante ristorante = new Ristorante();
        mapBaseFields(ristorante, payload, citta);

        ristorante.setSpecialita(payload.getSpecialita());
        ristorante.setFasciaPrezzoMedio(new RangePrezzo(payload.getPrezzoMin(), payload.getPrezzoMax()));
        ristorante.setTipologiaCucina(payload.getTipologiaCucina());
        ristorante.setPrenotazioniOnline(payload.getPrenotazioniOnline());
        ristorante.setDelivery(payload.getDelivery());

        return ristoranteRepository.save(ristorante);
    }

    public Ristorante update(UUID id, RistorantePayload payload) {
        Ristorante esistente = findById(id);
        Citta citta = cittaService.findById(payload.getCittaId());

        if (payload.getPrezzoMin().compareTo(payload.getPrezzoMax()) > 0) {
            throw new RuntimeException("Il prezzo minimo non può essere maggiore del prezzo massimo");
        }

        mapBaseFields(esistente, payload, citta);
        esistente.setSpecialita(payload.getSpecialita());
        esistente.setFasciaPrezzoMedio(new RangePrezzo(payload.getPrezzoMin(), payload.getPrezzoMax()));
        esistente.setTipologiaCucina(payload.getTipologiaCucina());
        esistente.setPrenotazioniOnline(payload.getPrenotazioniOnline());
        esistente.setDelivery(payload.getDelivery());

        return ristoranteRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        ristoranteRepository.deleteById(id);
    }

    public List<Ristorante> findByTipologiaCucina(biagioCota.biagio.enums.TipologiaCucina tipologiaCucina) {
        return ristoranteRepository.findByTipologiaCucina(tipologiaCucina);
    }

    public List<Ristorante> findConDelivery(Boolean delivery) {
        return ristoranteRepository.findByDelivery(delivery);
    }

    public List<Ristorante> findConPrenotazioniOnline(Boolean prenotazioniOnline) {
        return ristoranteRepository.findByPrenotazioniOnline(prenotazioniOnline);
    }

    public List<Ristorante> findByCittaId(UUID cittaId) {
        return ristoranteRepository.findByCittàId(cittaId);
    }

    public List<Ristorante> searchBySpecialita(String specialita) {
        return ristoranteRepository.findBySpecialitaContainingIgnoreCase(specialita);
    }

    private void mapBaseFields(Ristorante r, RistorantePayload payload, Citta citta) {
        r.setName(payload.getName());
        r.setDescrizione(payload.getDescrizione());
        r.setTipologia(payload.getTipologia());
        r.setIndirizzo(new Indirizzo(payload.getIndirizzo().getVia(), payload.getIndirizzo().getNumeroCivico()));
        r.setCittà(citta);
        r.setTelefono(payload.getTelefono());
        r.setEmail(payload.getEmail());
        r.setSitoWebURL(payload.getSitoWebURL());
        r.setAccessoDisabili(payload.getAccessoDisabili());
        r.setAttributiAggiuntivi(payload.getAttributiAggiuntivi());

        if (payload.getBusinessOwnerId() != null) {
            BusinessOwner owner = businessOwnerService.findById(payload.getBusinessOwnerId());
            r.setBusinessOwner(owner);
        }

        List<OrarioApertura> orari = payload.getOrariApertura().stream()
                .map(op -> new OrarioApertura(op.getGiorno(), op.getApertura(), op.getChiusura(), op.getChiuso(), op.getOrdine()))
                .toList();
        r.setOrariApertura(orari);
    }
}
