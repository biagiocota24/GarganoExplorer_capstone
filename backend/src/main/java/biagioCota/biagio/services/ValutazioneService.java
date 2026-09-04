package biagioCota.biagio.services;

import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.entities.Valutazione;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.exceptions.BusinessException;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.payloads.ValutazioneCreatePayload;
import biagioCota.biagio.payloads.ValutazioneResponse;
import biagioCota.biagio.repositories.ValutazioneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ValutazioneService {

    private final ValutazioneRepository valutazioneRepository;
    private final VisitorService visitorService;
    private final StrutturaService strutturaService;

    public ValutazioneService(ValutazioneRepository valutazioneRepository,
                              VisitorService visitorService,
                              StrutturaService strutturaService) {
        this.valutazioneRepository = valutazioneRepository;
        this.visitorService = visitorService;
        this.strutturaService = strutturaService;
    }

    public Valutazione findById(UUID id) {
        return valutazioneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Valutazione non trovata con id: " + id));
    }

    @Transactional
    public ValutazioneResponse createFromEmail(ValutazioneCreatePayload payload, String email) {
        Visitor autore = visitorService.findByEmail(email);
        Struttura struttura = strutturaService.findById(payload.getStrutturaId());

        if (valutazioneRepository.existsByAutoreAndStruttura(autore, struttura)) {
            throw new BusinessException("Hai già valutato questa struttura");
        }

        Valutazione v = new Valutazione();
        v.setAutore(autore);
        v.setStruttura(struttura);
        v.setStelle(payload.getStelle());
        v.setTitolo(payload.getTitolo());
        v.setCommento(payload.getCommento());
        v.setDataCreazione(LocalDateTime.now());
        v.setUtileCount(0);

        return ValutazioneResponse.fromEntity(valutazioneRepository.save(v));
    }

    @Transactional
    public ValutazioneResponse update(UUID id, ValutazioneCreatePayload payload, String email) {
        Valutazione esistente = findById(id);
        if (!esistente.getAutore().getEmail().equals(email)) {
            throw new BusinessException("Non puoi modificare la valutazione di un altro utente");
        }
        esistente.setStelle(payload.getStelle());
        esistente.setTitolo(payload.getTitolo());
        esistente.setCommento(payload.getCommento());
        return ValutazioneResponse.fromEntity(valutazioneRepository.save(esistente));
    }

    @Transactional
    public void delete(UUID id, String email) {
        Valutazione valutazione = findById(id);
        if (!valutazione.getAutore().getEmail().equals(email)) {
            throw new BusinessException("Non puoi eliminare la valutazione di un altro utente");
        }
        valutazioneRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ValutazioneResponse> findByStrutturaId(UUID strutturaId) {
        return valutazioneRepository.findByStrutturaIdOrderByDataCreazioneDesc(strutturaId)
                .stream().map(ValutazioneResponse::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public List<ValutazioneResponse> findByEmail(String email) {
        Visitor autore = visitorService.findByEmail(email);
        return valutazioneRepository.findByAutore(autore)
                .stream().map(ValutazioneResponse::fromEntity).toList();
    }

    public long countByStruttura(UUID strutturaId) {
        return valutazioneRepository.countByStrutturaId(strutturaId);
    }
}
