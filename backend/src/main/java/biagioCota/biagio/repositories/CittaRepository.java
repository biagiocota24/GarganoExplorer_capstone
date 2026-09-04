package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.Citta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CittaRepository extends JpaRepository<Citta, UUID> {

    Optional<Citta> findByName(String name);

    Optional<Citta> findByCAP(String cap);

    List<Citta> findByProvincia(String provincia);

    List<Citta> findByNameContainingIgnoreCase(String name);

    boolean existsByName(String name);

    boolean existsByCAP(String cap);
}
