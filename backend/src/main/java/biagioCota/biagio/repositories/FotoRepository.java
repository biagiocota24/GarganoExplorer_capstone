package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.Foto;
import biagioCota.biagio.entities.Struttura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FotoRepository extends JpaRepository<Foto, Long> {

    List<Foto> findByStruttura(Struttura struttura);

    List<Foto> findByStrutturaId(UUID strutturaId);

    List<Foto> findByStrutturaIdOrderByPosizioneAsc(UUID strutturaId);

    List<Foto> findByVisibile(Boolean visibile);

    List<Foto> findByStrutturaIdAndVisibile(UUID strutturaId, Boolean visibile);

    List<Foto> findByCaricataDaUserId(UUID userId);

    long countByStrutturaId(UUID strutturaId);
}
