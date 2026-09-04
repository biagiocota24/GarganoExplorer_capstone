package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Indirizzo;
import biagioCota.biagio.entities.OrarioApertura;
import biagioCota.biagio.entities.strutturaSubclasses.Negozio;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.enums.TipoMerce;
import biagioCota.biagio.payloads.strutture.NegozioPayload;
import biagioCota.biagio.repositories.NegozioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NegozioService {

    private final NegozioRepository negozioRepository;
    private final CittaService cittaService;
    private final BusinessOwnerService businessOwnerService;

    public NegozioService(NegozioRepository negozioRepository,
                          CittaService cittaService,
                          BusinessOwnerService businessOwnerService) {
        this.negozioRepository = negozioRepository;
        this.cittaService = cittaService;
        this.businessOwnerService = businessOwnerService;
    }

    public List<Negozio> findAll() {
        return negozioRepository.findAll();
    }

    public Negozio findById(UUID id) {
        return negozioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Negozio non trovato con id: " + id));
    }

    public Negozio save(NegozioPayload payload) {
        Citta citta = cittaService.findById(payload.getCittaId());

        Negozio negozio = new Negozio();
        mapBaseFields(negozio, payload, citta);
        negozio.setTipiMerce(payload.getTipoMerce());
        negozio.setSpedizioni(payload.getSpedizioni());

        return negozioRepository.save(negozio);
    }

    public Negozio update(UUID id, NegozioPayload payload) {
        Negozio esistente = findById(id);
        Citta citta = cittaService.findById(payload.getCittaId());
        mapBaseFields(esistente, payload, citta);
        esistente.setTipiMerce(payload.getTipoMerce());
        esistente.setSpedizioni(payload.getSpedizioni());
        return negozioRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        negozioRepository.deleteById(id);
    }

    public List<Negozio> findConSpedizioni(Boolean spedizioni) {
        return negozioRepository.findBySpedizioni(spedizioni);
    }

    public List<Negozio> findByTipoMerce(TipoMerce tipoMerce) {
        return negozioRepository.findByTipiMerceContaining(tipoMerce);
    }

    public List<Negozio> findByCittaId(UUID cittaId) {
        return negozioRepository.findByCittàId(cittaId);
    }

    public List<Negozio> searchByName(String name) {
        return negozioRepository.findByNameContainingIgnoreCase(name);
    }

    private void mapBaseFields(Negozio n, NegozioPayload payload, Citta citta) {
        n.setName(payload.getName());
        n.setDescrizione(payload.getDescrizione());
        n.setTipologia(payload.getTipologia());
        n.setIndirizzo(new Indirizzo(payload.getIndirizzo().getVia(), payload.getIndirizzo().getNumeroCivico()));
        n.setCittà(citta);
        n.setTelefono(payload.getTelefono());
        n.setEmail(payload.getEmail());
        n.setSitoWebURL(payload.getSitoWebURL());
        n.setAccessoDisabili(payload.getAccessoDisabili());
        n.setAttributiAggiuntivi(payload.getAttributiAggiuntivi());

        if (payload.getBusinessOwnerId() != null) {
            BusinessOwner owner = businessOwnerService.findById(payload.getBusinessOwnerId());
            n.setBusinessOwner(owner);
        }

        List<OrarioApertura> orari = payload.getOrariApertura().stream()
                .map(op -> new OrarioApertura(op.getGiorno(), op.getApertura(), op.getChiusura(), op.getChiuso(), op.getOrdine()))
                .toList();
        n.setOrariApertura(orari);
    }
}
