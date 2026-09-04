package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.Preferito;
import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PreferitoRepository extends JpaRepository<Preferito, UUID> {

    List<Preferito> findByAutore(Visitor autore);

    List<Preferito> findByAutoreId(UUID autoreId);

    List<Preferito> findByStruttura(Struttura struttura);

    List<Preferito> findByStrutturaId(UUID strutturaId);

    List<Preferito> findByAutoreIdAndVisitato(UUID autoreId, Boolean visitato);

    Optional<Preferito> findByAutoreAndStruttura(Visitor autore, Struttura struttura);

    boolean existsByAutoreAndStruttura(Visitor autore, Struttura struttura);

    boolean existsByAutoreIdAndStrutturaId(UUID autoreId, UUID strutturaId);

    long countByAutoreId(UUID autoreId);
}
