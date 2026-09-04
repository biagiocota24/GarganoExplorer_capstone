package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.strutturaSubclasses.Negozio;
import biagioCota.biagio.enums.TipoMerce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NegozioRepository extends JpaRepository<Negozio, UUID> {

    List<Negozio> findBySpedizioni(Boolean spedizioni);

    List<Negozio> findByTipiMerceContaining(TipoMerce tipoMerce);

    List<Negozio> findByCittàId(UUID cittaId);

    List<Negozio> findByNameContainingIgnoreCase(String name);
}
