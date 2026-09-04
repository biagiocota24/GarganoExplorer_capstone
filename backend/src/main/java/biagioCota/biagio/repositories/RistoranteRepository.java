package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.RangePrezzo;
import biagioCota.biagio.entities.strutturaSubclasses.Ristorante;
import biagioCota.biagio.enums.TipologiaCucina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RistoranteRepository extends JpaRepository<Ristorante, UUID> {

    List<Ristorante> findByTipologiaCucina(TipologiaCucina tipologiaCucina);

    List<Ristorante> findByDelivery(Boolean delivery);

    List<Ristorante> findByPrenotazioniOnline(Boolean prenotazioniOnline);

    List<Ristorante> findByFasciaPrezzoMedio(RangePrezzo fasciaPrezzoMedio);

    List<Ristorante> findByTipologiaCucinaAndDelivery(TipologiaCucina tipologiaCucina, Boolean delivery);

    List<Ristorante> findByCittàId(UUID cittaId);

    List<Ristorante> findBySpecialitaContainingIgnoreCase(String specialita);
}
