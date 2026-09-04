package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Indirizzo;
import biagioCota.biagio.entities.OrarioApertura;
import biagioCota.biagio.entities.strutturaSubclasses.Servizio;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.enums.TipoServizio;
import biagioCota.biagio.payloads.strutture.ServizioPayload;
import biagioCota.biagio.repositories.ServizioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServizioService {

    private final ServizioRepository servizioRepository;
    private final CittaService cittaService;
    private final BusinessOwnerService businessOwnerService;

    public ServizioService(ServizioRepository servizioRepository,
                           CittaService cittaService,
                           BusinessOwnerService businessOwnerService) {
        this.servizioRepository = servizioRepository;
        this.cittaService = cittaService;
        this.businessOwnerService = businessOwnerService;
    }

    public List<Servizio> findAll() {
        return servizioRepository.findAll();
    }

    public Servizio findById(UUID id) {
        return servizioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servizio non trovato con id: " + id));
    }

    public Servizio save(ServizioPayload payload) {
        Citta citta = cittaService.findById(payload.getCittaId());

        Servizio servizio = new Servizio();
        mapBaseFields(servizio, payload, citta);
        servizio.setTipoServizio(payload.getTipoServizio());
        servizio.setH24(payload.getH24());

        return servizioRepository.save(servizio);
    }

    public Servizio update(UUID id, ServizioPayload payload) {
        Servizio esistente = findById(id);
        Citta citta = cittaService.findById(payload.getCittaId());
        mapBaseFields(esistente, payload, citta);
        esistente.setTipoServizio(payload.getTipoServizio());
        esistente.setH24(payload.getH24());
        return servizioRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        servizioRepository.deleteById(id);
    }

    public List<Servizio> findByTipo(TipoServizio tipoServizio) {
        return servizioRepository.findByTipoServizio(tipoServizio);
    }

    public List<Servizio> findH24(Boolean h24) {
        return servizioRepository.findByH24(h24);
    }

    public List<Servizio> findByCittaId(UUID cittaId) {
        return servizioRepository.findByCittàId(cittaId);
    }

    public List<Servizio> findByTipoEH24(TipoServizio tipoServizio, Boolean h24) {
        return servizioRepository.findByTipoServizioAndH24(tipoServizio, h24);
    }

    private void mapBaseFields(Servizio s, ServizioPayload payload, Citta citta) {
        s.setName(payload.getName());
        s.setDescrizione(payload.getDescrizione());
        s.setTipologia(payload.getTipologia());
        s.setIndirizzo(new Indirizzo(payload.getIndirizzo().getVia(), payload.getIndirizzo().getNumeroCivico()));
        s.setCittà(citta);
        s.setTelefono(payload.getTelefono());
        s.setEmail(payload.getEmail());
        s.setSitoWebURL(payload.getSitoWebURL());
        s.setAccessoDisabili(payload.getAccessoDisabili());
        s.setAttributiAggiuntivi(payload.getAttributiAggiuntivi());

        if (payload.getBusinessOwnerId() != null) {
            BusinessOwner owner = businessOwnerService.findById(payload.getBusinessOwnerId());
            s.setBusinessOwner(owner);
        }

        List<OrarioApertura> orari = payload.getOrariApertura().stream()
                .map(op -> new OrarioApertura(op.getGiorno(), op.getApertura(), op.getChiusura(), op.getChiuso(), op.getOrdine()))
                .toList();
        s.setOrariApertura(orari);
    }
}
