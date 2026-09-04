package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.Commento;
import biagioCota.biagio.entities.PostCommunity;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentoRepository extends JpaRepository<Commento, UUID> {

    List<Commento> findByAutore(Visitor autore);

    List<Commento> findByAutoreId(UUID autoreId);

    List<Commento> findByPost(PostCommunity post);

    List<Commento> findByPostId(Long postId);

    List<Commento> findByMiPiaceGreaterThan(Integer minMiPiace);

    List<Commento> findByPostIdOrderByDataCreazioneDesc(Long postId);

    long countByPostId(Long postId);
}
