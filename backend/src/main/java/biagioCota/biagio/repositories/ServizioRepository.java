package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.strutturaSubclasses.Servizio;
import biagioCota.biagio.enums.TipoServizio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServizioRepository extends JpaRepository<Servizio, UUID> {

    List<Servizio> findByTipoServizio(TipoServizio tipoServizio);

    List<Servizio> findByH24(Boolean h24);

    List<Servizio> findByCittàId(UUID cittaId);

    List<Servizio> findByTipoServizioAndH24(TipoServizio tipoServizio, Boolean h24);
}
