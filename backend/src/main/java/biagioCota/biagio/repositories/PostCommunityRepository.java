package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.PostCommunity;
import biagioCota.biagio.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostCommunityRepository extends JpaRepository<PostCommunity, Long> {

    List<PostCommunity> findByCitta(Citta citta);

    List<PostCommunity> findByCittaId(UUID cittaId);

    List<PostCommunity> findByAutore(User autore);

    List<PostCommunity> findByAutoreId(UUID autoreId);

    List<PostCommunity> findByTitoloContainingIgnoreCase(String titolo);

    List<PostCommunity> findByContenutoContainingIgnoreCase(String parola);

    List<PostCommunity> findByDataCreazioneAfter(LocalDateTime from);

    List<PostCommunity> findByDataCreazioneBetween(LocalDateTime from, LocalDateTime to);

    List<PostCommunity> findByCittaIdOrderByDataCreazioneDesc(UUID cittaId);

    long countByCittaId(UUID cittaId);

    long countByAutoreId(UUID autoreId);
}
