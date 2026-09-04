package biagioCota.biagio.services;

import biagioCota.biagio.entities.LogAzione;
import biagioCota.biagio.entities.userSubclasses.Admin;
import biagioCota.biagio.enums.StatoAzione;
import biagioCota.biagio.enums.TipoAzione;
import biagioCota.biagio.payloads.LogAzionePayload;
import biagioCota.biagio.repositories.LogAzioneRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LogAzioneService {

    private final LogAzioneRepository logAzioneRepository;
    private final AdminService adminService;

    public LogAzioneService(LogAzioneRepository logAzioneRepository, AdminService adminService) {
        this.logAzioneRepository = logAzioneRepository;
        this.adminService = adminService;
    }

    public List<LogAzione> findAll() {
        return logAzioneRepository.findAll();
    }

    public LogAzione findById(UUID id) {
        return logAzioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LogAzione non trovato con id: " + id));
    }

    public LogAzione save(LogAzionePayload payload) {
        Admin admin = adminService.findById(payload.getAdminId());

        LogAzione log = new LogAzione();
        log.setAdmin(admin);
        log.setTipoAzione(payload.getTipoAzione());
        log.setEntitaId(payload.getEntitaId());
        log.setTipoEntita(payload.getTipoEntita());
        log.setDataAzione(LocalDateTime.now());
        log.setDatiPrecedenti(payload.getDatiPrecedenti());
        log.setDatiSuccessivi(payload.getDatiSuccessivi());
        log.setStato(StatoAzione.SUCCESSO);

        return logAzioneRepository.save(log);
    }

    public void delete(UUID id) {
        findById(id);
        logAzioneRepository.deleteById(id);
    }

    public List<LogAzione> findByAdmin(Admin admin) {
        return logAzioneRepository.findByAdmin(admin);
    }

    public List<LogAzione> findByAdminId(UUID adminId) {
        return logAzioneRepository.findByAdminId(adminId);
    }

    public List<LogAzione> findByTipoAzione(TipoAzione tipoAzione) {
        return logAzioneRepository.findByTipoAzione(tipoAzione);
    }

    public List<LogAzione> findByStato(StatoAzione stato) {
        return logAzioneRepository.findByStato(stato);
    }

    public List<LogAzione> findByTipoEntita(String tipoEntita) {
        return logAzioneRepository.findByTipoEntita(tipoEntita);
    }

    public List<LogAzione> findByEntitaId(UUID entitaId) {
        return logAzioneRepository.findByEntitaId(entitaId);
    }

    public List<LogAzione> findDopo(LocalDateTime from) {
        return logAzioneRepository.findByDataAzioneAfter(from);
    }

    public List<LogAzione> findFra(LocalDateTime from, LocalDateTime to) {
        return logAzioneRepository.findByDataAzioneBetween(from, to);
    }

    public List<LogAzione> findByAdminEAzione(UUID adminId, TipoAzione tipoAzione) {
        return logAzioneRepository.findByAdminIdAndTipoAzione(adminId, tipoAzione);
    }

    public List<LogAzione> findByAdminOrdinati(UUID adminId) {
        return logAzioneRepository.findByAdminIdOrderByDataAzioneDesc(adminId);
    }

    public long countByAdminEStato(UUID adminId, StatoAzione stato) {
        return logAzioneRepository.countByAdminIdAndStato(adminId, stato);
    }
}
