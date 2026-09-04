package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Messaggio;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.enums.StatoMessaggio;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.payloads.MessaggioCreatePayload;
import biagioCota.biagio.repositories.MessaggioRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MessaggioService {

    private final MessaggioRepository messaggioRepository;
    private final VisitorService visitorService;
    private final CittaService cittaService;

    public MessaggioService(MessaggioRepository messaggioRepository,
                            VisitorService visitorService,
                            CittaService cittaService) {
        this.messaggioRepository = messaggioRepository;
        this.visitorService = visitorService;
        this.cittaService = cittaService;
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findAll() {
        return messaggioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Messaggio findById(Long id) {
        return messaggioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Messaggio non trovato con id: " + id));
    }

    @Transactional
    public Messaggio saveFromEmail(MessaggioCreatePayload payload, String email) {
        Visitor autore = visitorService.findByEmail(email);
        Citta citta = cittaService.findById(payload.getCittaId());

        Messaggio messaggio = new Messaggio();
        messaggio.setAutore(autore);
        messaggio.setCitta(citta);
        messaggio.setContenuto(payload.getContenuto());
        messaggio.setFotoUrl(payload.getFotoUrl());
        messaggio.setDataCreazione(LocalDateTime.now());
        messaggio.setContatoreMiPiace(0);
        messaggio.setStato(StatoMessaggio.PUBBLICATO);

        if (payload.getRispostaAId() != null) {
            Messaggio padre = findById(payload.getRispostaAId());
            messaggio.setRispostaA(padre);
        }

        return messaggioRepository.save(messaggio);
    }

    @Transactional
    public Messaggio update(Long id, MessaggioCreatePayload payload, String email) {
        Messaggio messaggio = findById(id);
        if (messaggio.getAutore() == null || !messaggio.getAutore().getEmail().equals(email)) {
            throw new AccessDeniedException("Non sei l'autore di questo messaggio");
        }
        messaggio.setContenuto(payload.getContenuto());
        if (payload.getFotoUrl() != null) messaggio.setFotoUrl(payload.getFotoUrl());
        messaggio.setDataModifica(LocalDateTime.now());
        return messaggioRepository.save(messaggio);
    }

    @Transactional
    public void deleteIfOwnerOrAdmin(Long id, String email, boolean isAdmin) {
        Messaggio messaggio = findById(id);
        if (!isAdmin && (messaggio.getAutore() == null || !messaggio.getAutore().getEmail().equals(email))) {
            throw new AccessDeniedException("Non sei autorizzato a eliminare questo messaggio");
        }
        messaggioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findByCitta(Citta citta) {
        return messaggioRepository.findByCitta(citta);
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findByCittaId(UUID cittaId) {
        return messaggioRepository.findByCittaId(cittaId);
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findByAutore(Visitor autore) {
        return messaggioRepository.findByAutore(autore);
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findByAutoreId(UUID autoreId) {
        return messaggioRepository.findByAutoreId(autoreId);
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findByStato(StatoMessaggio stato) {
        return messaggioRepository.findByStato(stato);
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findByCittaEStato(UUID cittaId, StatoMessaggio stato) {
        return messaggioRepository.findByCittaIdAndStato(cittaId, stato);
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findPrincipaliDiCitta(UUID cittaId) {
        return messaggioRepository.findByRispostaAIsNullAndCittaId(cittaId);
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findRisposteAId(Long messaggioId) {
        return messaggioRepository.findByRispostaAId(messaggioId);
    }

    @Transactional(readOnly = true)
    public List<Messaggio> findByCittaOrdinati(UUID cittaId) {
        return messaggioRepository.findByCittaIdOrderByDataCreazioneDesc(cittaId);
    }

    @Transactional(readOnly = true)
    public long countByCitta(UUID cittaId) {
        return messaggioRepository.countByCittaId(cittaId);
    }
}
