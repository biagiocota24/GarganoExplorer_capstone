package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Indirizzo;
import biagioCota.biagio.entities.OrarioApertura;
import biagioCota.biagio.entities.strutturaSubclasses.StabilimentoBalneare;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.enums.TipoSpiaggia;
import biagioCota.biagio.payloads.strutture.StabilimentoBalnearePayload;
import biagioCota.biagio.repositories.StabilimentoBalneareRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StabilimentoBalneareService {

    private final StabilimentoBalneareRepository stabilimentoBalneareRepository;
    private final CittaService cittaService;
    private final BusinessOwnerService businessOwnerService;

    public StabilimentoBalneareService(StabilimentoBalneareRepository stabilimentoBalneareRepository,
                                       CittaService cittaService,
                                       BusinessOwnerService businessOwnerService) {
        this.stabilimentoBalneareRepository = stabilimentoBalneareRepository;
        this.cittaService = cittaService;
        this.businessOwnerService = businessOwnerService;
    }

    public List<StabilimentoBalneare> findAll() {
        return stabilimentoBalneareRepository.findAll();
    }

    public StabilimentoBalneare findById(UUID id) {
        return stabilimentoBalneareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("StabilimentoBalneare non trovato con id: " + id));
    }

    public StabilimentoBalneare save(StabilimentoBalnearePayload payload) {
        Citta citta = cittaService.findById(payload.getCittaId());

        StabilimentoBalneare stabilimento = new StabilimentoBalneare();
        mapBaseFields(stabilimento, payload, citta);
        stabilimento.setTipoSpiaggia(payload.getTipoSpiaggia());
        stabilimento.setPrezzoOmbrellone(payload.getPrezzoOmbrellone());
        stabilimento.setDocciaPresente(payload.getDocciaPresente());
        stabilimento.setBarPresente(payload.getBarPresente());
        stabilimento.setRistorazionePresente(payload.getRistorazionePresente());

        return stabilimentoBalneareRepository.save(stabilimento);
    }

    public StabilimentoBalneare update(UUID id, StabilimentoBalnearePayload payload) {
        StabilimentoBalneare esistente = findById(id);
        Citta citta = cittaService.findById(payload.getCittaId());
        mapBaseFields(esistente, payload, citta);
        esistente.setTipoSpiaggia(payload.getTipoSpiaggia());
        esistente.setPrezzoOmbrellone(payload.getPrezzoOmbrellone());
        esistente.setDocciaPresente(payload.getDocciaPresente());
        esistente.setBarPresente(payload.getBarPresente());
        esistente.setRistorazionePresente(payload.getRistorazionePresente());
        return stabilimentoBalneareRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        stabilimentoBalneareRepository.deleteById(id);
    }

    public List<StabilimentoBalneare> findByTipoSpiaggia(TipoSpiaggia tipoSpiaggia) {
        return stabilimentoBalneareRepository.findByTipoSpiaggia(tipoSpiaggia);
    }

    public List<StabilimentoBalneare> findConBar(boolean barPresente) {
        return stabilimentoBalneareRepository.findByBarPresente(barPresente);
    }

    public List<StabilimentoBalneare> findConRistorazione(boolean ristorazionePresente) {
        return stabilimentoBalneareRepository.findByRistorazionePresente(ristorazionePresente);
    }

    public List<StabilimentoBalneare> findEntroPrezzo(double prezzoMassimo) {
        return stabilimentoBalneareRepository.findByPrezzoOmbrelloneLessThanEqual(prezzoMassimo);
    }

    public List<StabilimentoBalneare> findByCittaId(UUID cittaId) {
        return stabilimentoBalneareRepository.findByCittàId(cittaId);
    }

    public List<StabilimentoBalneare> findConBarERistorazione(boolean bar, boolean ristorazione) {
        return stabilimentoBalneareRepository.findByBarPresenteAndRistorazionePresente(bar, ristorazione);
    }

    private void mapBaseFields(StabilimentoBalneare s, StabilimentoBalnearePayload payload, Citta citta) {
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
