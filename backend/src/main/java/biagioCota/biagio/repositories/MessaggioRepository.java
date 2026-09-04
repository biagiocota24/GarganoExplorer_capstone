package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Messaggio;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.enums.StatoMessaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {

    List<Messaggio> findByCitta(Citta citta);

    List<Messaggio> findByCittaId(UUID cittaId);

    List<Messaggio> findByAutore(Visitor autore);

    List<Messaggio> findByAutoreId(UUID autoreId);

    List<Messaggio> findByStato(StatoMessaggio stato);

    List<Messaggio> findByCittaIdAndStato(UUID cittaId, StatoMessaggio stato);

    List<Messaggio> findByRispostaAIsNullAndCittaId(UUID cittaId);

    List<Messaggio> findByRispostaA(Messaggio messaggio);

    List<Messaggio> findByRispostaAId(Long messaggioId);

    List<Messaggio> findByDataCreazioneAfter(LocalDateTime from);

    List<Messaggio> findByCittaIdOrderByDataCreazioneDesc(UUID cittaId);

    long countByCittaId(UUID cittaId);
}
