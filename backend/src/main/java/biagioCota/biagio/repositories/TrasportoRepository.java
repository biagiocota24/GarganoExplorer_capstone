package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.strutturaSubclasses.Trasporto;
import biagioCota.biagio.enums.TipoTrasporto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrasportoRepository extends JpaRepository<Trasporto, UUID> {

    List<Trasporto> findByTipoTrasporto(TipoTrasporto tipoTrasporto);

    List<Trasporto> findByPagamentoDigitale(Boolean pagamentoDigitale);

    List<Trasporto> findByCittàId(UUID cittaId);

    List<Trasporto> findByTipoTrasportoAndPagamentoDigitale(TipoTrasporto tipoTrasporto, Boolean pagamentoDigitale);
}
