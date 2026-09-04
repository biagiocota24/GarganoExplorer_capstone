package biagioCota.biagio.services;

import biagioCota.biagio.entities.Preferito;
import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.exceptions.BusinessException;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.payloads.PreferitoAddPayload;
import biagioCota.biagio.payloads.PreferitoResponse;
import biagioCota.biagio.repositories.PreferitoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PreferitoService {

    private final PreferitoRepository preferitoRepository;
    private final VisitorService visitorService;
    private final StrutturaService strutturaService;

    public PreferitoService(PreferitoRepository preferitoRepository,
                            VisitorService visitorService,
                            StrutturaService strutturaService) {
        this.preferitoRepository = preferitoRepository;
        this.visitorService = visitorService;
        this.strutturaService = strutturaService;
    }

    public Preferito findById(UUID id) {
        return preferitoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Preferito non trovato con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<PreferitoResponse> findByEmail(String email) {
        Visitor autore = visitorService.findByEmail(email);
        return preferitoRepository.findByAutore(autore)
                .stream().map(PreferitoResponse::fromEntity).toList();
    }

    @Transactional
    public PreferitoResponse addByEmail(UUID strutturaId, PreferitoAddPayload payload, String email) {
        Visitor autore = visitorService.findByEmail(email);
        Struttura struttura = strutturaService.findById(strutturaId);

        if (preferitoRepository.existsByAutoreIdAndStrutturaId(autore.getId(), struttura.getId())) {
            throw new BusinessException("Struttura già nei preferiti");
        }

        Preferito preferito = new Preferito();
        preferito.setAutore(autore);
        preferito.setStruttura(struttura);
        preferito.setNota(payload != null ? payload.getNota() : null);
        preferito.setVisitato(payload != null && Boolean.TRUE.equals(payload.getVisitato()));
        preferito.setDataSalvataggio(LocalDateTime.now());

        return PreferitoResponse.fromEntity(preferitoRepository.save(preferito));
    }

    @Transactional
    public void removeByEmail(UUID id, String email) {
        Preferito preferito = findById(id);
        if (!preferito.getAutore().getEmail().equals(email)) {
            throw new BusinessException("Non puoi rimuovere il preferito di un altro utente");
        }
        preferitoRepository.deleteById(id);
    }

    public boolean isPreferito(UUID autoreId, UUID strutturaId) {
        return preferitoRepository.existsByAutoreIdAndStrutturaId(autoreId, strutturaId);
    }
}
