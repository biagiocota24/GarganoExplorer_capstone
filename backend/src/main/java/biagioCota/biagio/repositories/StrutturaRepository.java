package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.enums.TipologiaStruttura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StrutturaRepository extends JpaRepository<Struttura, UUID> {

    List<Struttura> findByTipologia(TipologiaStruttura tipologia);

    Page<Struttura> findByTipologia(TipologiaStruttura tipologia, Pageable pageable);

    Page<Struttura> findByCittàId(UUID cittaId, Pageable pageable);

    Page<Struttura> findByTipologiaAndCittàId(TipologiaStruttura tipologia, UUID cittaId, Pageable pageable);

    List<Struttura> findByCittà(Citta citta);

    List<Struttura> findByCittàId(UUID cittaId);

    List<Struttura> findByTipologiaAndCittà(TipologiaStruttura tipologia, Citta citta);

    List<Struttura> findByNameContainingIgnoreCase(String name);

    List<Struttura> findByAccessoDisabili(Boolean accessoDisabili);

    List<Struttura> findByBusinessOwner(BusinessOwner businessOwner);

    List<Struttura> findByBusinessOwnerId(UUID businessOwnerId);

    boolean existsByNameAndCittà(String name, Citta citta);
}
