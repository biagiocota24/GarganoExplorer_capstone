package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Indirizzo;
import biagioCota.biagio.entities.OrarioApertura;
import biagioCota.biagio.entities.strutturaSubclasses.AttrazionePrincipale;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.enums.TipoAttrazione;
import biagioCota.biagio.payloads.strutture.AttrazionePrincipalePayload;
import biagioCota.biagio.repositories.AttrazionePrincipaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AttrazionePrincipaleService {

    private final AttrazionePrincipaleRepository attrazionePrincipaleRepository;
    private final CittaService cittaService;
    private final BusinessOwnerService businessOwnerService;

    public AttrazionePrincipaleService(AttrazionePrincipaleRepository attrazionePrincipaleRepository,
                                       CittaService cittaService,
                                       BusinessOwnerService businessOwnerService) {
        this.attrazionePrincipaleRepository = attrazionePrincipaleRepository;
        this.cittaService = cittaService;
        this.businessOwnerService = businessOwnerService;
    }

    public List<AttrazionePrincipale> findAll() {
        return attrazionePrincipaleRepository.findAll();
    }

    public AttrazionePrincipale findById(UUID id) {
        return attrazionePrincipaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AttrazionePrincipale non trovata con id: " + id));
    }

    public AttrazionePrincipale save(AttrazionePrincipalePayload payload) {
        Citta citta = cittaService.findById(payload.getCittaId());

        AttrazionePrincipale attrazione = new AttrazionePrincipale();
        mapBaseFields(attrazione, payload, citta);
        attrazione.setTipoAttrazione(payload.getTipoAttrazione());
        attrazione.setBigliettoEntrata(payload.getBigliettoEntrata());

        return attrazionePrincipaleRepository.save(attrazione);
    }

    public AttrazionePrincipale update(UUID id, AttrazionePrincipalePayload payload) {
        AttrazionePrincipale esistente = findById(id);
        Citta citta = cittaService.findById(payload.getCittaId());
        mapBaseFields(esistente, payload, citta);
        esistente.setTipoAttrazione(payload.getTipoAttrazione());
        esistente.setBigliettoEntrata(payload.getBigliettoEntrata());
        return attrazionePrincipaleRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        attrazionePrincipaleRepository.deleteById(id);
    }

    public List<AttrazionePrincipale> findByTipo(TipoAttrazione tipoAttrazione) {
        return attrazionePrincipaleRepository.findByTipoAttrazione(tipoAttrazione);
    }

    public List<AttrazionePrincipale> findEntroPrezzo(double prezzoMassimo) {
        return attrazionePrincipaleRepository.findByBigliettoEntrataLessThanEqual(prezzoMassimo);
    }

    public List<AttrazionePrincipale> findByCittaId(UUID cittaId) {
        return attrazionePrincipaleRepository.findByCittàId(cittaId);
    }

    public List<AttrazionePrincipale> findByTipoAndCitta(TipoAttrazione tipoAttrazione, UUID cittaId) {
        return attrazionePrincipaleRepository.findByTipoAttrazioneAndCittàId(tipoAttrazione, cittaId);
    }

    private void mapBaseFields(AttrazionePrincipale a, AttrazionePrincipalePayload payload, Citta citta) {
        a.setName(payload.getName());
        a.setDescrizione(payload.getDescrizione());
        a.setTipologia(payload.getTipologia());
        a.setIndirizzo(new Indirizzo(payload.getIndirizzo().getVia(), payload.getIndirizzo().getNumeroCivico()));
        a.setCittà(citta);
        a.setTelefono(payload.getTelefono());
        a.setEmail(payload.getEmail());
        a.setSitoWebURL(payload.getSitoWebURL());
        a.setAccessoDisabili(payload.getAccessoDisabili());
        a.setAttributiAggiuntivi(payload.getAttributiAggiuntivi());

        if (payload.getBusinessOwnerId() != null) {
            BusinessOwner owner = businessOwnerService.findById(payload.getBusinessOwnerId());
            a.setBusinessOwner(owner);
        }

        List<OrarioApertura> orari = payload.getOrariApertura().stream()
                .map(op -> new OrarioApertura(op.getGiorno(), op.getApertura(), op.getChiusura(), op.getChiuso(), op.getOrdine()))
                .toList();
        a.setOrariApertura(orari);
    }
}
