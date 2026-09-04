package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.strutturaSubclasses.AttrazionePrincipale;
import biagioCota.biagio.enums.TipoAttrazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttrazionePrincipaleRepository extends JpaRepository<AttrazionePrincipale, UUID> {

    List<AttrazionePrincipale> findByTipoAttrazione(TipoAttrazione tipoAttrazione);

    List<AttrazionePrincipale> findByBigliettoEntrataLessThanEqual(double prezzoMassimo);

    List<AttrazionePrincipale> findByBigliettoEntrata(double bigliettoEntrata);

    List<AttrazionePrincipale> findByCittàId(UUID cittaId);

    List<AttrazionePrincipale> findByTipoAttrazioneAndCittàId(TipoAttrazione tipoAttrazione, UUID cittaId);
}
