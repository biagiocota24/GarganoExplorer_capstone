package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.strutturaSubclasses.StabilimentoBalneare;
import biagioCota.biagio.enums.TipoSpiaggia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StabilimentoBalneareRepository extends JpaRepository<StabilimentoBalneare, UUID> {

    List<StabilimentoBalneare> findByTipoSpiaggia(TipoSpiaggia tipoSpiaggia);

    List<StabilimentoBalneare> findByBarPresente(boolean barPresente);

    List<StabilimentoBalneare> findByRistorazionePresente(boolean ristorazionePresente);

    List<StabilimentoBalneare> findByDocciaPresente(Boolean docciaPresente);

    List<StabilimentoBalneare> findByPrezzoOmbrelloneLessThanEqual(double prezzoMassimo);

    List<StabilimentoBalneare> findByCittàId(UUID cittaId);

    List<StabilimentoBalneare> findByBarPresenteAndRistorazionePresente(boolean barPresente, boolean ristorazionePresente);
}
