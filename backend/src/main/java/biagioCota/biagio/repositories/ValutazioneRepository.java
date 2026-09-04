package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.entities.Valutazione;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ValutazioneRepository extends JpaRepository<Valutazione, UUID> {

    List<Valutazione> findByAutore(Visitor autore);

    List<Valutazione> findByAutoreId(UUID autoreId);

    List<Valutazione> findByStruttura(Struttura struttura);

    List<Valutazione> findByStrutturaId(UUID strutturaId);

    List<Valutazione> findByStelle(Integer stelle);

    List<Valutazione> findByStelleGreaterThanEqual(Integer stelle);

    List<Valutazione> findByStrutturaIdOrderByStelleDesc(UUID strutturaId);

    List<Valutazione> findByStrutturaIdOrderByDataCreazioneDesc(UUID strutturaId);

    Optional<Valutazione> findByAutoreAndStruttura(Visitor autore, Struttura struttura);

    boolean existsByAutoreAndStruttura(Visitor autore, Struttura struttura);

    long countByStrutturaId(UUID strutturaId);
}
