package biagioCota.biagio.repositories;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.strutturaSubclasses.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    List<Hotel> findByStelle(int stelle);

    List<Hotel> findByStelleGreaterThanEqual(int stelle);

    List<Hotel> findByWifi(boolean wifi);

    List<Hotel> findByPiscina(boolean piscina);

    List<Hotel> findByAnimaliAmmessi(boolean animaliAmmessi);

    List<Hotel> findByParcheggioPrivato(boolean parcheggioPrivato);

    List<Hotel> findByPrezzoMedioNotteLessThanEqual(double prezzoMassimo);

    List<Hotel> findByCittàId(UUID cittaId);

    List<Hotel> findByCittàAndStelle(Citta citta, int stelle);

    List<Hotel> findByStelleAndWifiAndPiscina(int stelle, boolean wifi, boolean piscina);
}
