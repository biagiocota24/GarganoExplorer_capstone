package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Indirizzo;
import biagioCota.biagio.entities.OrarioApertura;
import biagioCota.biagio.entities.strutturaSubclasses.Trasporto;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.enums.TipoTrasporto;
import biagioCota.biagio.payloads.strutture.TrasportoPayload;
import biagioCota.biagio.repositories.TrasportoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TrasportoService {

    private final TrasportoRepository trasportoRepository;
    private final CittaService cittaService;
    private final BusinessOwnerService businessOwnerService;

    public TrasportoService(TrasportoRepository trasportoRepository,
                            CittaService cittaService,
                            BusinessOwnerService businessOwnerService) {
        this.trasportoRepository = trasportoRepository;
        this.cittaService = cittaService;
        this.businessOwnerService = businessOwnerService;
    }

    public List<Trasporto> findAll() {
        return trasportoRepository.findAll();
    }

    public Trasporto findById(UUID id) {
        return trasportoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trasporto non trovato con id: " + id));
    }

    public Trasporto save(TrasportoPayload payload) {
        Citta citta = cittaService.findById(payload.getCittaId());

        Trasporto trasporto = new Trasporto();
        mapBaseFields(trasporto, payload, citta);
        trasporto.setTipoTrasporto(payload.getTipoTrasporto());
        trasporto.setPagamentoDigitale(payload.getPagamentoDigitale());

        return trasportoRepository.save(trasporto);
    }

    public Trasporto update(UUID id, TrasportoPayload payload) {
        Trasporto esistente = findById(id);
        Citta citta = cittaService.findById(payload.getCittaId());
        mapBaseFields(esistente, payload, citta);
        esistente.setTipoTrasporto(payload.getTipoTrasporto());
        esistente.setPagamentoDigitale(payload.getPagamentoDigitale());
        return trasportoRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        trasportoRepository.deleteById(id);
    }

    public List<Trasporto> findByTipo(TipoTrasporto tipoTrasporto) {
        return trasportoRepository.findByTipoTrasporto(tipoTrasporto);
    }

    public List<Trasporto> findConPagamentoDigitale(Boolean pagamentoDigitale) {
        return trasportoRepository.findByPagamentoDigitale(pagamentoDigitale);
    }

    public List<Trasporto> findByCittaId(UUID cittaId) {
        return trasportoRepository.findByCittàId(cittaId);
    }

    public List<Trasporto> findByTipoEPagamento(TipoTrasporto tipoTrasporto, Boolean pagamentoDigitale) {
        return trasportoRepository.findByTipoTrasportoAndPagamentoDigitale(tipoTrasporto, pagamentoDigitale);
    }

    private void mapBaseFields(Trasporto t, TrasportoPayload payload, Citta citta) {
        t.setName(payload.getName());
        t.setDescrizione(payload.getDescrizione());
        t.setTipologia(payload.getTipologia());
        t.setIndirizzo(new Indirizzo(payload.getIndirizzo().getVia(), payload.getIndirizzo().getNumeroCivico()));
        t.setCittà(citta);
        t.setTelefono(payload.getTelefono());
        t.setEmail(payload.getEmail());
        t.setSitoWebURL(payload.getSitoWebURL());
        t.setAccessoDisabili(payload.getAccessoDisabili());
        t.setAttributiAggiuntivi(payload.getAttributiAggiuntivi());

        if (payload.getBusinessOwnerId() != null) {
            BusinessOwner owner = businessOwnerService.findById(payload.getBusinessOwnerId());
            t.setBusinessOwner(owner);
        }

        List<OrarioApertura> orari = payload.getOrariApertura().stream()
                .map(op -> new OrarioApertura(op.getGiorno(), op.getApertura(), op.getChiusura(), op.getChiuso(), op.getOrdine()))
                .toList();
        t.setOrariApertura(orari);
    }
}
