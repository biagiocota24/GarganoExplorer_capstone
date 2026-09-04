package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.LogAzione;
import biagioCota.biagio.entities.userSubclasses.Admin;
import biagioCota.biagio.enums.StatoAzione;
import biagioCota.biagio.enums.TipoAzione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface LogAzioneRepository extends JpaRepository<LogAzione, UUID> {

    List<LogAzione> findByAdmin(Admin admin);

    List<LogAzione> findByAdminId(UUID adminId);

    List<LogAzione> findByTipoAzione(TipoAzione tipoAzione);

    List<LogAzione> findByStato(StatoAzione stato);

    List<LogAzione> findByTipoEntita(String tipoEntita);

    List<LogAzione> findByEntitaId(UUID entitaId);

    List<LogAzione> findByDataAzioneAfter(LocalDateTime from);

    List<LogAzione> findByDataAzioneBetween(LocalDateTime from, LocalDateTime to);

    List<LogAzione> findByAdminIdAndTipoAzione(UUID adminId, TipoAzione tipoAzione);

    List<LogAzione> findByAdminIdOrderByDataAzioneDesc(UUID adminId);

    long countByAdminIdAndStato(UUID adminId, StatoAzione stato);
}
