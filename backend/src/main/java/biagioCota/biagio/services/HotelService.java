package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.Indirizzo;
import biagioCota.biagio.entities.OrarioApertura;
import biagioCota.biagio.entities.strutturaSubclasses.Hotel;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.payloads.strutture.HotelPayload;
import biagioCota.biagio.repositories.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final CittaService cittaService;
    private final BusinessOwnerService businessOwnerService;

    public HotelService(HotelRepository hotelRepository,
                        CittaService cittaService,
                        BusinessOwnerService businessOwnerService) {
        this.hotelRepository = hotelRepository;
        this.cittaService = cittaService;
        this.businessOwnerService = businessOwnerService;
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public Hotel findById(UUID id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel non trovato con id: " + id));
    }

    public Hotel save(HotelPayload payload) {
        Citta citta = cittaService.findById(payload.getCittaId());

        Hotel hotel = new Hotel();
        mapBaseFields(hotel, payload, citta);

        hotel.setStelle(payload.getStelle());
        hotel.setPrezzoMedioNotte(payload.getPrezzoMedioNotte());
        hotel.setWifi(payload.getWifi());
        hotel.setParcheggioPrivato(payload.getParcheggioPrivato());
        hotel.setPiscina(payload.getPiscina());
        hotel.setAnimaliAmmessi(payload.getAnimaliAmmessi());

        return hotelRepository.save(hotel);
    }

    public Hotel update(UUID id, HotelPayload payload) {
        Hotel esistente = findById(id);
        Citta citta = cittaService.findById(payload.getCittaId());
        mapBaseFields(esistente, payload, citta);

        esistente.setStelle(payload.getStelle());
        esistente.setPrezzoMedioNotte(payload.getPrezzoMedioNotte());
        esistente.setWifi(payload.getWifi());
        esistente.setParcheggioPrivato(payload.getParcheggioPrivato());
        esistente.setPiscina(payload.getPiscina());
        esistente.setAnimaliAmmessi(payload.getAnimaliAmmessi());

        return hotelRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        hotelRepository.deleteById(id);
    }

    public List<Hotel> findByStelle(int stelle) {
        return hotelRepository.findByStelle(stelle);
    }

    public List<Hotel> findByStelleMinime(int stelle) {
        return hotelRepository.findByStelleGreaterThanEqual(stelle);
    }

    public List<Hotel> findByWifi(boolean wifi) {
        return hotelRepository.findByWifi(wifi);
    }

    public List<Hotel> findByPiscina(boolean piscina) {
        return hotelRepository.findByPiscina(piscina);
    }

    public List<Hotel> findByAnimaliAmmessi(boolean animaliAmmessi) {
        return hotelRepository.findByAnimaliAmmessi(animaliAmmessi);
    }

    public List<Hotel> findByParcheggioPrivato(boolean parcheggio) {
        return hotelRepository.findByParcheggioPrivato(parcheggio);
    }

    public List<Hotel> findEntroPrezzo(double prezzoMassimo) {
        return hotelRepository.findByPrezzoMedioNotteLessThanEqual(prezzoMassimo);
    }

    public List<Hotel> findByCittaId(UUID cittaId) {
        return hotelRepository.findByCittàId(cittaId);
    }

    public List<Hotel> findByCittaAndStelle(Citta citta, int stelle) {
        return hotelRepository.findByCittàAndStelle(citta, stelle);
    }

    public List<Hotel> findByFiltri(int stelle, boolean wifi, boolean piscina) {
        return hotelRepository.findByStelleAndWifiAndPiscina(stelle, wifi, piscina);
    }

    private void mapBaseFields(Hotel hotel, HotelPayload payload, Citta citta) {
        hotel.setName(payload.getName());
        hotel.setDescrizione(payload.getDescrizione());
        hotel.setTipologia(payload.getTipologia());
        hotel.setIndirizzo(new Indirizzo(payload.getIndirizzo().getVia(), payload.getIndirizzo().getNumeroCivico()));
        hotel.setCittà(citta);
        hotel.setTelefono(payload.getTelefono());
        hotel.setEmail(payload.getEmail());
        hotel.setSitoWebURL(payload.getSitoWebURL());
        hotel.setAccessoDisabili(payload.getAccessoDisabili());
        hotel.setAttributiAggiuntivi(payload.getAttributiAggiuntivi());

        if (payload.getBusinessOwnerId() != null) {
            BusinessOwner owner = businessOwnerService.findById(payload.getBusinessOwnerId());
            hotel.setBusinessOwner(owner);
        }

        List<OrarioApertura> orari = payload.getOrariApertura().stream()
                .map(op -> new OrarioApertura(op.getGiorno(), op.getApertura(), op.getChiusura(), op.getChiuso(), op.getOrdine()))
                .toList();
        hotel.setOrariApertura(orari);
    }
}
