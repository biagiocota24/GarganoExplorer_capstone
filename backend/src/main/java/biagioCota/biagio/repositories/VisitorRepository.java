package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.enums.Nazionalita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, UUID> {

    Optional<Visitor> findByEmail(String email);

    List<Visitor> findByNazionalita(Nazionalita nazionalita);

    List<Visitor> findByActive(boolean active);

    List<Visitor> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(String name, String surname);

    boolean existsByEmail(String email);
}
