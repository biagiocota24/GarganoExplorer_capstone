package biagioCota.biagio.configuration;

import biagioCota.biagio.entities.*;
import biagioCota.biagio.entities.strutturaSubclasses.*;
import biagioCota.biagio.entities.userSubclasses.*;
import biagioCota.biagio.enums.*;
import biagioCota.biagio.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private CittaRepository cittaRepository;
    @Autowired private StrutturaRepository strutturaRepository;
    @Autowired private FotoRepository fotoRepository;
    @Autowired private ValutazioneRepository valutazioneRepository;
    @Autowired private PreferitoRepository preferitoRepository;
    @Autowired private MessaggioRepository messaggioRepository;
    @Autowired private PostCommunityRepository postCommunityRepository;
    @Autowired private CommentoRepository commentoRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        String pw = passwordEncoder.encode("Password123!");

        // ============================================================
        // UTENTI
        // ============================================================
        Admin admin1 = (Admin) userRepository.save(new Admin(
                "Mario", "Rossi", "mario.admin@gargano.it", pw,
                "Amministratore principale della piattaforma", "3301234567",
                "https://i.pravatar.cc/150?img=1", LocalDate.of(2024, 1, 15)));

        Admin admin2 = (Admin) userRepository.save(new Admin(
                "Anna", "Bianchi", "anna.admin@gargano.it", pw,
                "Gestione contenuti e moderazione", "3309876543",
                "https://i.pravatar.cc/150?img=2", LocalDate.of(2024, 3, 10)));

        BusinessOwner owner1 = (BusinessOwner) userRepository.save(new BusinessOwner(
                "Giuseppe", "Verdi", "giuseppe@gargano.it", pw,
                "Ristoratore appassionato della cucina garganica dal 2005", "3312345678",
                "https://i.pravatar.cc/150?img=3"));

        BusinessOwner owner2 = (BusinessOwner) userRepository.save(new BusinessOwner(
                "Isabella", "Ferrari", "isabella@gargano.it", pw,
                "Gestisce strutture ricettive sul Gargano da 15 anni", "3323456789",
                "https://i.pravatar.cc/150?img=4"));

        BusinessOwner owner3 = (BusinessOwner) userRepository.save(new BusinessOwner(
                "Paolo", "Moretti", "paolo@gargano.it", pw,
                "Imprenditore turistico, specializzato in attività marine", "3334567890",
                "https://i.pravatar.cc/150?img=5"));

        Visitor v1 = (Visitor) userRepository.save(new Visitor(
                "Luca", "Conti", "luca@gmail.com", pw,
                "Appassionato di gastronomia e viaggi enogastronomici", "3401234567",
                "https://i.pravatar.cc/150?img=6", Nazionalita.IT));

        Visitor v2 = (Visitor) userRepository.save(new Visitor(
                "Anna", "Schmidt", "anna.schmidt@gmail.de", pw,
                "Amante di natura e spiagge del Mediterraneo", "3402345678",
                "https://i.pravatar.cc/150?img=7", Nazionalita.DE));

        Visitor v3 = (Visitor) userRepository.save(new Visitor(
                "Marie", "Dupont", "marie.dupont@gmail.fr", pw,
                "Viaggiatrice esperta, esploro destinazioni autentiche", "3403456789",
                "https://i.pravatar.cc/150?img=8", Nazionalita.FR));

        Visitor v4 = (Visitor) userRepository.save(new Visitor(
                "Carlos", "Rodriguez", "carlos@gmail.es", pw,
                "Turista spagnolo in cerca di esperienze locali", "3404567890",
                "https://i.pravatar.cc/150?img=9", Nazionalita.ES));

        Visitor v5 = (Visitor) userRepository.save(new Visitor(
                "Lisa", "Mueller", "lisa@gmail.at", pw,
                "Vacanziera austriaca, adoro il sole del Sud Italia", "3405678901",
                "https://i.pravatar.cc/150?img=10", Nazionalita.AT));

        Visitor v6 = (Visitor) userRepository.save(new Visitor(
                "Giovanni", "Bianchi", "giovanni@gmail.com", pw,
                "Fotografo e escursionista, esploro il Gargano ogni estate", "3406789012",
                "https://i.pravatar.cc/150?img=11", Nazionalita.IT));

        Visitor v7 = (Visitor) userRepository.save(new Visitor(
                "Sophie", "Martin", "sophie@gmail.ch", pw,
                "Blogger di viaggio svizzera, scrivo di destinazioni mediterranee", "3407890123",
                "https://i.pravatar.cc/150?img=12", Nazionalita.CH));

        Visitor v8 = (Visitor) userRepository.save(new Visitor(
                "Marco", "Ferrari", "marco@gmail.it", pw,
                "Pugliese che ama riscoprire le bellezze della sua terra", "3408901234",
                "https://i.pravatar.cc/150?img=13", Nazionalita.IT));

        // ============================================================
        // CITTÀ DEL GARGANO
        // ============================================================
        Citta vieste       = cittaRepository.save(new Citta(null, "Vieste",              "71019", "FG", null));
        Citta peschici     = cittaRepository.save(new Citta(null, "Peschici",            "71010", "FG", null));
        Citta rodiGarganico= cittaRepository.save(new Citta(null, "Rodi Garganico",      "71012", "FG", null));
        Citta manfredonia  = cittaRepository.save(new Citta(null, "Manfredonia",         "71043", "FG", null));
        Citta monteSantAngelo = cittaRepository.save(new Citta(null, "Monte Sant'Angelo","71037", "FG", null));
        Citta sanGiovanni  = cittaRepository.save(new Citta(null, "San Giovanni Rotondo","71013", "FG", null));
        Citta mattinata    = cittaRepository.save(new Citta(null, "Mattinata",           "71030", "FG", null));
        Citta vicoDelGargano = cittaRepository.save(new Citta(null, "Vico del Gargano", "71018", "FG", null));

        // ============================================================
        // HOTEL
        // ============================================================
        Hotel hotel1 = new Hotel();
        hotel1.setName("Hotel Gargano Palace");
        hotel1.setDescrizione("Elegante hotel sul promontorio con vista panoramica sul mare Adriatico. Camere lussuose, piscina infinity e ristorante gourmet.");
        hotel1.setTipologia(TipologiaStruttura.HOTEL);
        hotel1.setCittà(vieste);
        hotel1.setIndirizzo(new Indirizzo("Via del Castello", "12"));
        hotel1.setTelefono("0884123456");
        hotel1.setEmail("info@garganopalace.it");
        hotel1.setSitoWebURL("https://www.garganopalace.it");
        hotel1.setAccessoDisabili(true);
        hotel1.setBusinessOwner(owner1);
        hotel1.setStelle(4);
        hotel1.setPrezzoMedioNotte(180.0);
        hotel1.setWifi(true);
        hotel1.setParcheggioPrivato(true);
        hotel1.setPiscina(true);
        hotel1.setAnimaliAmmessi(false);
        addOrariApertura(hotel1, LocalTime.of(0, 0), LocalTime.of(23, 59));
        Hotel savedHotel1 = (Hotel) strutturaRepository.save(hotel1);
        saveFoto(savedHotel1, "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=800", 0);
        saveFoto(savedHotel1, "https://images.unsplash.com/photo-1582719508461-905c673771fd?w=800", 1);
        saveFoto(savedHotel1, "https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=800", 2);

        Hotel hotel2 = new Hotel();
        hotel2.setName("Hotel La Spiaggia");
        hotel2.setDescrizione("Hotel direttamente sul mare a Peschici, ideale per famiglie. Spiaggia privata convenzionata e animazione estiva.");
        hotel2.setTipologia(TipologiaStruttura.HOTEL);
        hotel2.setCittà(peschici);
        hotel2.setIndirizzo(new Indirizzo("Lungomare Colombo", "5"));
        hotel2.setTelefono("0884987654");
        hotel2.setEmail("info@hotelspiaggia.it");
        hotel2.setSitoWebURL("https://www.hotellaspiaggiapeschici.it");
        hotel2.setAccessoDisabili(true);
        hotel2.setBusinessOwner(owner2);
        hotel2.setStelle(3);
        hotel2.setPrezzoMedioNotte(110.0);
        hotel2.setWifi(true);
        hotel2.setParcheggioPrivato(false);
        hotel2.setPiscina(false);
        hotel2.setAnimaliAmmessi(true);
        addOrariApertura(hotel2, LocalTime.of(0, 0), LocalTime.of(23, 59));
        Hotel savedHotel2 = (Hotel) strutturaRepository.save(hotel2);
        saveFoto(savedHotel2, "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=800", 0);
        saveFoto(savedHotel2, "https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=800", 1);

        Hotel hotel3 = new Hotel();
        hotel3.setName("Hotel Monte Sacro");
        hotel3.setDescrizione("Struttura storica nel cuore di Monte Sant'Angelo, a pochi passi dalla Basilica di San Michele. Atmosfera raccolta e autentica.");
        hotel3.setTipologia(TipologiaStruttura.HOTEL);
        hotel3.setCittà(monteSantAngelo);
        hotel3.setIndirizzo(new Indirizzo("Via San Michele", "8"));
        hotel3.setTelefono("0884562341");
        hotel3.setEmail("prenotazioni@hotelmontesacro.it");
        hotel3.setAccessoDisabili(false);
        hotel3.setBusinessOwner(owner1);
        hotel3.setStelle(4);
        hotel3.setPrezzoMedioNotte(135.0);
        hotel3.setWifi(true);
        hotel3.setParcheggioPrivato(true);
        hotel3.setPiscina(false);
        hotel3.setAnimaliAmmessi(false);
        addOrariApertura(hotel3, LocalTime.of(0, 0), LocalTime.of(23, 59));
        Hotel savedHotel3 = (Hotel) strutturaRepository.save(hotel3);
        saveFoto(savedHotel3, "https://images.unsplash.com/photo-1455587734955-081b22074882?w=800", 0);
        saveFoto(savedHotel3, "https://images.unsplash.com/photo-1445019980597-93fa8acb246c?w=800", 1);

        // ============================================================
        // RISTORANTI
        // ============================================================
        Ristorante rist1 = new Ristorante();
        rist1.setName("La Taverna del Pescatore");
        rist1.setDescrizione("Ristorante di pesce fresco nel porto di Vieste. Ogni giorno il pescato arriva direttamente dalle barche locali. Specialità: zuppa di pesce alla garganica.");
        rist1.setTipologia(TipologiaStruttura.RISTORANTE);
        rist1.setCittà(vieste);
        rist1.setIndirizzo(new Indirizzo("Via del Porto", "3"));
        rist1.setTelefono("0884701234");
        rist1.setEmail("info@tavernapescatore.it");
        rist1.setSitoWebURL("https://www.tavernadelpescatore.it");
        rist1.setAccessoDisabili(true);
        rist1.setBusinessOwner(owner1);
        rist1.setSpecialita("Zuppa di pesce alla garganica, polpo alla brace, tiella di riso");
        rist1.setFasciaPrezzoMedio(new RangePrezzo(new BigDecimal("25"), new BigDecimal("50")));
        rist1.setTipologiaCucina(TipologiaCucina.PESCE);
        rist1.setPrenotazioniOnline(true);
        rist1.setDelivery(false);
        addOrariApertura(rist1, LocalTime.of(12, 0), LocalTime.of(23, 0));
        Ristorante savedRist1 = (Ristorante) strutturaRepository.save(rist1);
        saveFoto(savedRist1, "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800", 0);
        saveFoto(savedRist1, "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?w=800", 1);

        Ristorante rist2 = new Ristorante();
        rist2.setName("Osteria del Gargano");
        rist2.setDescrizione("Cucina tradizionale pugliese in un'osteria rustica nel centro storico di Rodi Garganico. Orecchiette, cicorie e prodotti a km zero.");
        rist2.setTipologia(TipologiaStruttura.RISTORANTE);
        rist2.setCittà(rodiGarganico);
        rist2.setIndirizzo(new Indirizzo("Piazza Madre Chiesa", "14"));
        rist2.setTelefono("0884966321");
        rist2.setEmail("osteria@gargano.it");
        rist2.setAccessoDisabili(false);
        rist2.setBusinessOwner(owner2);
        rist2.setSpecialita("Orecchiette alle cime di rapa, agnello alla brace, friselle");
        rist2.setFasciaPrezzoMedio(new RangePrezzo(new BigDecimal("15"), new BigDecimal("30")));
        rist2.setTipologiaCucina(TipologiaCucina.PUGLIESE);
        rist2.setPrenotazioniOnline(false);
        rist2.setDelivery(false);
        addOrariApertura(rist2, LocalTime.of(12, 30), LocalTime.of(22, 30));
        Ristorante savedRist2 = (Ristorante) strutturaRepository.save(rist2);
        saveFoto(savedRist2, "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800", 0);
        saveFoto(savedRist2, "https://images.unsplash.com/photo-1476224203421-9ac39bcb3327?w=800", 1);

        Ristorante rist3 = new Ristorante();
        rist3.setName("Pizzeria da Luigi");
        rist3.setDescrizione("La migliore pizza napoletana a Manfredonia. Forno a legna, impasto a lunga lievitazione, ingredienti DOP. Ambiente familiare e accogliente.");
        rist3.setTipologia(TipologiaStruttura.RISTORANTE);
        rist3.setCittà(manfredonia);
        rist3.setIndirizzo(new Indirizzo("Corso Manfredi", "45"));
        rist3.setTelefono("0884581234");
        rist3.setEmail("pizzeriadaluigi@email.it");
        rist3.setAccessoDisabili(true);
        rist3.setBusinessOwner(owner3);
        rist3.setSpecialita("Pizza margherita DOP, pizza con fiordilatte di Agerola, calzone fritto");
        rist3.setFasciaPrezzoMedio(new RangePrezzo(new BigDecimal("10"), new BigDecimal("20")));
        rist3.setTipologiaCucina(TipologiaCucina.PIZZA);
        rist3.setPrenotazioniOnline(true);
        rist3.setDelivery(true);
        addOrariApertura(rist3, LocalTime.of(11, 30), LocalTime.of(23, 30));
        Ristorante savedRist3 = (Ristorante) strutturaRepository.save(rist3);
        saveFoto(savedRist3, "https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800", 0);
        saveFoto(savedRist3, "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=800", 1);

        Ristorante rist4 = new Ristorante();
        rist4.setName("Ristorante Il Veliero");
        rist4.setDescrizione("Ristorante con terrazza sul mare a Peschici. Frutti di mare freschi e carpacci di tonno. La vista al tramonto è mozzafiato.");
        rist4.setTipologia(TipologiaStruttura.RISTORANTE);
        rist4.setCittà(peschici);
        rist4.setIndirizzo(new Indirizzo("Via Marina", "21"));
        rist4.setTelefono("0884964512");
        rist4.setEmail("ilveliero@peschici.it");
        rist4.setSitoWebURL("https://www.ilvelieropeschici.it");
        rist4.setAccessoDisabili(false);
        rist4.setBusinessOwner(owner1);
        rist4.setSpecialita("Crudo di mare, linguine agli scampi, zuppa di cozze e vongole");
        rist4.setFasciaPrezzoMedio(new RangePrezzo(new BigDecimal("30"), new BigDecimal("60")));
        rist4.setTipologiaCucina(TipologiaCucina.FRUTTI_DI_MARE);
        rist4.setPrenotazioniOnline(true);
        rist4.setDelivery(false);
        addOrariApertura(rist4, LocalTime.of(12, 0), LocalTime.of(23, 0));
        Ristorante savedRist4 = (Ristorante) strutturaRepository.save(rist4);
        saveFoto(savedRist4, "https://images.unsplash.com/photo-1559339352-11d035aa65de?w=800", 0);
        saveFoto(savedRist4, "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=800", 1);

        // ============================================================
        // STABILIMENTI BALNEARI
        // ============================================================
        StabilimentoBalneare stab1 = new StabilimentoBalneare();
        stab1.setName("Lido Adriatico");
        stab1.setDescrizione("Lo stabilimento balneare più attrezzato di Vieste. Sabbia finissima dorata, acque cristalline e animazione tutto il giorno. Ottimo per famiglie.");
        stab1.setTipologia(TipologiaStruttura.STABILIMENTO_BALNEARE);
        stab1.setCittà(vieste);
        stab1.setIndirizzo(new Indirizzo("Lungomare Enrico Mattei", "1"));
        stab1.setTelefono("0884703456");
        stab1.setEmail("info@lidoadriatico.it");
        stab1.setAccessoDisabili(true);
        stab1.setBusinessOwner(owner2);
        stab1.setTipoSpiaggia(TipoSpiaggia.SABBIA);
        stab1.setPrezzoOmbrellone(25.0);
        stab1.setDocciaPresente(true);
        stab1.setBarPresente(true);
        stab1.setRistorazionePresente(true);
        addOrariStagionali(stab1);
        StabilimentoBalneare savedStab1 = (StabilimentoBalneare) strutturaRepository.save(stab1);
        saveFoto(savedStab1, "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800", 0);
        saveFoto(savedStab1, "https://images.unsplash.com/photo-1519046904884-53103b34b206?w=800", 1);

        StabilimentoBalneare stab2 = new StabilimentoBalneare();
        stab2.setName("Spiaggia Baia delle Zagare");
        stab2.setDescrizione("Spiaggia incantata tra faraglioni di roccia bianca a Mattinata. Una delle più belle del Gargano, accessibile via mare o sentiero.");
        stab2.setTipologia(TipologiaStruttura.SPIAGGIA);
        stab2.setCittà(mattinata);
        stab2.setIndirizzo(new Indirizzo("Baia delle Zagare", "1"));
        stab2.setTelefono("0884559876");
        stab2.setEmail("info@baiadellezagare.it");
        stab2.setSitoWebURL("https://www.baiadellezagare.it");
        stab2.setAccessoDisabili(false);
        stab2.setBusinessOwner(owner2);
        stab2.setTipoSpiaggia(TipoSpiaggia.SABBIA);
        stab2.setPrezzoOmbrellone(30.0);
        stab2.setDocciaPresente(true);
        stab2.setBarPresente(true);
        stab2.setRistorazionePresente(false);
        addOrariStagionali(stab2);
        StabilimentoBalneare savedStab2 = (StabilimentoBalneare) strutturaRepository.save(stab2);
        saveFoto(savedStab2, "https://images.unsplash.com/photo-1472214103451-9374bd1c798e?w=800", 0);
        saveFoto(savedStab2, "https://images.unsplash.com/photo-1473116763249-2faaef81ccda?w=800", 1);

        StabilimentoBalneare stab3 = new StabilimentoBalneare();
        stab3.setName("Grotte di Peschici Beach Club");
        stab3.setDescrizione("Stabilimento tra le scogliere di Peschici. Piattaforme tuffatrici, snorkeling tra le grotte marine e aperitivi al tramonto.");
        stab3.setTipologia(TipologiaStruttura.STABILIMENTO_BALNEARE);
        stab3.setCittà(peschici);
        stab3.setIndirizzo(new Indirizzo("Contrada Grotte", "5"));
        stab3.setTelefono("0884962341");
        stab3.setEmail("info@grottepeschici.it");
        stab3.setAccessoDisabili(false);
        stab3.setBusinessOwner(owner3);
        stab3.setTipoSpiaggia(TipoSpiaggia.SCOGLI);
        stab3.setPrezzoOmbrellone(20.0);
        stab3.setDocciaPresente(true);
        stab3.setBarPresente(true);
        stab3.setRistorazionePresente(false);
        addOrariStagionali(stab3);
        StabilimentoBalneare savedStab3 = (StabilimentoBalneare) strutturaRepository.save(stab3);
        saveFoto(savedStab3, "https://images.unsplash.com/photo-1518709268805-4e9042af9178?w=800", 0);

        // ============================================================
        // NEGOZI
        // ============================================================
        Negozio neg1 = new Negozio();
        neg1.setName("Bottega del Gargano");
        neg1.setDescrizione("Negozio di prodotti tipici locali: olio extravergine DOP, vini del Gargano, conserve artigianali, ceramiche di Vico del Gargano.");
        neg1.setTipologia(TipologiaStruttura.NEGOZIO);
        neg1.setCittà(vicoDelGargano);
        neg1.setIndirizzo(new Indirizzo("Via Roma", "7"));
        neg1.setTelefono("0884991234");
        neg1.setEmail("bottega@gargano.it");
        neg1.setAccessoDisabili(true);
        neg1.setBusinessOwner(owner3);
        neg1.setTipiMerce(List.of(TipoMerce.OLIO_EXTRAVERGINE, TipoMerce.VINI_LIQUORI,
                TipoMerce.MIELE_CONSERVE, TipoMerce.CERAMICA_ARTIGIANALE, TipoMerce.SOUVENIR_TIPICI));
        neg1.setSpedizioni(true);
        addOrari(neg1, LocalTime.of(9, 0), LocalTime.of(13, 0), LocalTime.of(17, 0), LocalTime.of(20, 0));
        Negozio savedNeg1 = (Negozio) strutturaRepository.save(neg1);
        saveFoto(savedNeg1, "https://images.unsplash.com/photo-1472851294608-062f824d29cc?w=800", 0);
        saveFoto(savedNeg1, "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=800", 1);

        Negozio neg2 = new Negozio();
        neg2.setName("Mare e Sole Shop");
        neg2.setDescrizione("Il negozio perfetto per prepararsi alla spiaggia: costumi, teli mare, attrezzatura snorkeling e tutto il necessario per le vacanze al mare.");
        neg2.setTipologia(TipologiaStruttura.NEGOZIO);
        neg2.setCittà(vieste);
        neg2.setIndirizzo(new Indirizzo("Corso Fazzini", "18"));
        neg2.setTelefono("0884705678");
        neg2.setEmail("mareesole@vieste.it");
        neg2.setAccessoDisabili(true);
        neg2.setBusinessOwner(owner1);
        neg2.setTipiMerce(List.of(TipoMerce.ABBIGLIAMENTO_MARE, TipoMerce.ARTICOLI_SPIAGGIA,
                TipoMerce.ATTREZZATURA_NUOTO, TipoMerce.OCCHIALI));
        neg2.setSpedizioni(false);
        addOrari(neg2, LocalTime.of(9, 30), LocalTime.of(13, 30), LocalTime.of(17, 30), LocalTime.of(21, 0));
        Negozio savedNeg2 = (Negozio) strutturaRepository.save(neg2);
        saveFoto(savedNeg2, "https://images.unsplash.com/photo-1560343776-97e7d202ff0e?w=800", 0);

        // ============================================================
        // ATTRAZIONI PRINCIPALI
        // ============================================================
        AttrazionePrincipale attr1 = new AttrazionePrincipale();
        attr1.setName("Grotta di Sfinale");
        attr1.setDescrizione("Straordinaria grotta marina accessibile solo via mare, nei pressi di Peschici. Stalattiti, stalagmiti e un lago interno di acqua smeraldo.");
        attr1.setTipologia(TipologiaStruttura.ATTIVITA_ACQUA);
        attr1.setCittà(peschici);
        attr1.setIndirizzo(new Indirizzo("Costa di Peschici", "1"));
        attr1.setTelefono("0884963456");
        attr1.setEmail("info@grottasfinale.it");
        attr1.setAccessoDisabili(false);
        attr1.setBusinessOwner(owner2);
        attr1.setTipoAttrazione(TipoAttrazione.GROTTA_MARINA);
        attr1.setBigliettoEntrata(12.0);
        addOrariStagionali(attr1);
        AttrazionePrincipale savedAttr1 = (AttrazionePrincipale) strutturaRepository.save(attr1);
        saveFoto(savedAttr1, "https://images.unsplash.com/photo-1518709268805-4e9042af2170?w=800", 0);
        saveFoto(savedAttr1, "https://images.unsplash.com/photo-1504472478235-9bc48ba4d60f?w=800", 1);

        AttrazionePrincipale attr2 = new AttrazionePrincipale();
        attr2.setName("Santuario di San Michele Arcangelo");
        attr2.setDescrizione("Uno dei luoghi di pellegrinaggio più importanti d'Europa. La grotta sacra dove, secondo la tradizione, apparve l'Arcangelo Michele nel 490 d.C.");
        attr2.setTipologia(TipologiaStruttura.MUSEO);
        attr2.setCittà(monteSantAngelo);
        attr2.setIndirizzo(new Indirizzo("Via Reale Basilica", "127"));
        attr2.setTelefono("0884561150");
        attr2.setEmail("info@santuariosanmichele.it");
        attr2.setSitoWebURL("https://www.santuariosanmichele.it");
        attr2.setAccessoDisabili(true);
        attr2.setBusinessOwner(owner3);
        attr2.setTipoAttrazione(TipoAttrazione.CHIESA_ABBAZIA);
        attr2.setBigliettoEntrata(0.0);
        addOrari(attr2, LocalTime.of(7, 30), LocalTime.of(12, 30), LocalTime.of(14, 30), LocalTime.of(19, 0));
        AttrazionePrincipale savedAttr2 = (AttrazionePrincipale) strutturaRepository.save(attr2);
        saveFoto(savedAttr2, "https://images.unsplash.com/photo-1548625149-720f0d4da1e8?w=800", 0);
        saveFoto(savedAttr2, "https://images.unsplash.com/photo-1591985605022-000f3c9ddaf0?w=800", 1);

        AttrazionePrincipale attr3 = new AttrazionePrincipale();
        attr3.setName("Foresta Umbra");
        attr3.setDescrizione("Il cuore verde del Gargano: 11.000 ettari di faggeta secolare, frassini e aceri centenari. Il polmone verde della Puglia, parte del Parco Nazionale.");
        attr3.setTipologia(TipologiaStruttura.AREA_NATURALE);
        attr3.setCittà(vicoDelGargano);
        attr3.setIndirizzo(new Indirizzo("Strada Statale 528", "km 14"));
        attr3.setTelefono("0884991045");
        attr3.setEmail("info@forestaumbra.it");
        attr3.setSitoWebURL("https://www.parcogargano.it");
        attr3.setAccessoDisabili(false);
        attr3.setBusinessOwner(owner1);
        attr3.setTipoAttrazione(TipoAttrazione.PARCO_NATURALE);
        attr3.setBigliettoEntrata(5.0);
        addOrari(attr3, LocalTime.of(8, 0), LocalTime.of(20, 0), null, null);
        AttrazionePrincipale savedAttr3 = (AttrazionePrincipale) strutturaRepository.save(attr3);
        saveFoto(savedAttr3, "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?w=800", 0);
        saveFoto(savedAttr3, "https://images.unsplash.com/photo-1448375240586-882707db888b?w=800", 1);

        // ============================================================
        // SERVIZI
        // ============================================================
        Servizio serv1 = new Servizio();
        serv1.setName("InfoPoint Turismo Vieste");
        serv1.setDescrizione("Ufficio informazioni turistiche nel centro di Vieste. Staff multilingue, mappe, prenotazioni escursioni e consulenza gratuita per i visitatori.");
        serv1.setTipologia(TipologiaStruttura.NEGOZIO);
        serv1.setCittà(vieste);
        serv1.setIndirizzo(new Indirizzo("Piazza Kennedy", "1"));
        serv1.setTelefono("0884708111");
        serv1.setEmail("infopoint@comune.vieste.it");
        serv1.setAccessoDisabili(true);
        serv1.setBusinessOwner(owner2);
        serv1.setTipoServizio(TipoServizio.UFFICIO_TURISMO);
        serv1.setH24(false);
        addOrari(serv1, LocalTime.of(9, 0), LocalTime.of(13, 0), LocalTime.of(15, 0), LocalTime.of(19, 0));
        Servizio savedServ1 = (Servizio) strutturaRepository.save(serv1);
        saveFoto(savedServ1, "https://images.unsplash.com/photo-1464207687429-7505649dae38?w=800", 0);

        Servizio serv2 = new Servizio();
        serv2.setName("Centro Diving Gargano");
        serv2.setDescrizione("Scuola di immersioni con istruttori certificati PADI. Corsi per principianti e subacquei esperti, escursioni guidate nelle grotte marine.");
        serv2.setTipologia(TipologiaStruttura.ATTIVITA_ACQUA);
        serv2.setCittà(peschici);
        serv2.setIndirizzo(new Indirizzo("Porto di Peschici", "2"));
        serv2.setTelefono("0884965432");
        serv2.setEmail("diving@gargano.it");
        serv2.setSitoWebURL("https://www.divinggargano.it");
        serv2.setAccessoDisabili(false);
        serv2.setBusinessOwner(owner3);
        serv2.setTipoServizio(TipoServizio.SCUOLA_DIVING);
        serv2.setH24(false);
        addOrariStagionali(serv2);
        Servizio savedServ2 = (Servizio) strutturaRepository.save(serv2);
        saveFoto(savedServ2, "https://images.unsplash.com/photo-1559551409-dadc959f76b8?w=800", 0);
        saveFoto(savedServ2, "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=800", 1);

        // ============================================================
        // TRASPORTO
        // ============================================================
        Trasporto tras1 = new Trasporto();
        tras1.setName("Traghetto Vieste-Tremiti");
        tras1.setDescrizione("Collegamento marittimo giornaliero tra Vieste e le Isole Tremiti. Partenze mattutine, gita in giornata o pernottamento sulle isole.");
        tras1.setTipologia(TipologiaStruttura.TRASPORTO);
        tras1.setCittà(vieste);
        tras1.setIndirizzo(new Indirizzo("Porto di Vieste", "1"));
        tras1.setTelefono("0884707890");
        tras1.setEmail("biglietteria@traghettitremiti.it");
        tras1.setSitoWebURL("https://www.traghettitremiti.it");
        tras1.setAccessoDisabili(true);
        tras1.setBusinessOwner(owner3);
        tras1.setTipoTrasporto(TipoTrasporto.TRAGHETTO);
        tras1.setPagamentoDigitale(true);
        addOrariStagionali(tras1);
        Trasporto savedTras1 = (Trasporto) strutturaRepository.save(tras1);
        saveFoto(savedTras1, "https://images.unsplash.com/photo-1544620347-c4fd4a3d5957?w=800", 0);
        saveFoto(savedTras1, "https://images.unsplash.com/photo-1501854140801-50d01698950b?w=800", 1);

        // ============================================================
        // VALUTAZIONI
        // ============================================================
        saveVal(v1, savedHotel1, 5, "Hotel meraviglioso!", "La vista dal balcone è indimenticabile. Staff gentilissimo e piscina splendida.", -5);
        saveVal(v2, savedHotel1, 4, "Molto buono", "Ottimo hotel con ottima posizione. Camera spaziosa. Colazione abbondante.", -10);
        saveVal(v3, savedHotel1, 5, "Paradiso sul Gargano", "Torneremo sicuramente. Ogni dettaglio curato, il ristorante interno è eccellente.", -3);

        saveVal(v4, savedHotel2, 4, "Buon rapporto qualità prezzo", "Hotel semplice ma pulito e ben posizionato. Personale disponibile.", -7);
        saveVal(v5, savedHotel2, 3, "Nella media", "Hotel base, niente di speciale ma funzionale. Spiaggia vicina un plus.", -14);

        saveVal(v6, savedHotel3, 5, "Location da sogno", "Dormire a Monte Sant'Angelo è un'esperienza unica. Silenzio e storia ovunque.", -2);
        saveVal(v1, savedHotel3, 4, "Ottima base per escursioni", "Perfetto per visitare la basilica e i dintorni. Personale cordiale.", -8);

        saveVal(v2, savedRist1, 5, "Il miglior pesce del Gargano", "La zuppa di pesce è straordinaria. Ingredienti freschissimi, prezzi giusti.", -4);
        saveVal(v7, savedRist1, 5, "Esperienza indimenticabile", "Ho mangiato il polpo alla brace migliore della mia vita. Vista sul porto splendida.", -6);
        saveVal(v3, savedRist1, 4, "Ottimo ma prenotare", "Cucina eccellente, ma molto affollato d'estate. Prenotare assolutamente.", -9);

        saveVal(v8, savedRist2, 5, "Cucina pugliese autentica", "Orecchiette alle cime di rapa come quelle di mia nonna. Ambiente rustico e bello.", -3);
        saveVal(v4, savedRist2, 4, "Tradizione vera", "Cibo genuino e abbondante. Prezzi onesti per la qualità.", -11);

        saveVal(v5, savedRist3, 4, "Pizza fantastica", "Impasto leggero e ingredienti di qualità. La margherita DOP è imperdibile.", -5);
        saveVal(v6, savedRist4, 5, "Tramonto e crudo di mare", "La terrazza sul mare al tramonto vale già il viaggio. Il crudo di mare è freschissimo.", -2);
        saveVal(v1, savedRist4, 5, "Top assoluto", "Il miglior ristorante di Peschici senza dubbio. Lingue agli scampi da sogno.", -4);

        saveVal(v7, savedStab1, 4, "Spiaggia ben organizzata", "Ombrelloni ampi, docce pulite, bar attrezzato. Sabbia fine e mare pulito.", -6);
        saveVal(v2, savedStab2, 5, "Una delle più belle d'Italia", "Baia delle Zagare è mozzafiato. I faraglioni bianchi riflessi nel mare verde...", -8);
        saveVal(v3, savedAttr1, 5, "Grotta magica", "L'escursione in barca è imperdibile. La grotta dall'interno è spettacolare.", -7);
        saveVal(v8, savedAttr2, 5, "Luogo sacro e spirituale", "Bellissimo santuario con un'atmosfera unica. Storia millenaria in ogni pietra.", -10);
        saveVal(v4, savedAttr3, 5, "La foresta più bella del Sud", "Percorsi ben segnalati, aria pulitissima. I faggi centenari ti lasciano senza fiato.", -3);

        // ============================================================
        // PREFERITI
        // ============================================================
        savePref(v1, savedHotel1, "Tornarci per il nostro anniversario", true);
        savePref(v1, savedRist1, "Prenotare il tavolo in anticipo", true);
        savePref(v1, savedAttr3, "Fare il percorso lungo con tenda", false);
        savePref(v2, savedStab2, "Andare di mattina presto per evitare la folla", true);
        savePref(v2, savedHotel2, "Chiedere camera con balcone", false);
        savePref(v3, savedAttr1, "Portare maschera e boccaglio", true);
        savePref(v3, savedRist4, "Prenotare per il tramonto", false);
        savePref(v4, savedAttr2, "Visiting at dawn, magical atmosphere", true);

        // ============================================================
        // MESSAGGI COMMUNITY
        // ============================================================
        saveMsg(v1, vieste, "Ciao a tutti! Qualcuno conosce il modo migliore per raggiungere Baia delle Zagare da Vieste? Ho sentito che c'è un sentiero a piedi.", null);
        saveMsg(v6, vieste, "Risposta a Luca: il sentiero parte da Mattinata, circa 2 ore a piedi. In alternativa c'è il battello che parte dal porto ogni mattina alle 9:30!", null);
        saveMsg(v2, peschici, "Ho appena visitato la Grotta di Sfinale e sono rimasta senza parole. Se siete a Peschici è assolutamente da fare! Prenotate la barca in anticipo.", null);
        saveMsg(v3, monteSantAngelo, "Il Santuario di San Michele è un luogo davvero speciale. Anche per chi non è credente, l'atmosfera è unica. Da vedere assolutamente!", null);
        saveMsg(v8, rodiGarganico, "Qualcuno sa se l'Osteria del Gargano accetta prenotazioni? Ho provato a chiamare ma non risponde nessuno. Grazie!", null);
        saveMsg(v7, vieste, "Tramonti a Vieste: il belvedere del centro storico è il posto migliore per vederli. Andate almeno 20 minuti prima per trovare posto!", null);
        saveMsg(v4, mattinata, "La Baia delle Zagare in settembre è perfetta: meno gente, acqua ancora calda e colori autunnali sulle rocce. Consigliatissima!", null);

        // ============================================================
        // POST COMMUNITY
        // ============================================================
        PostCommunity post1 = new PostCommunity();
        post1.setAutore(v6);
        post1.setCitta(vieste);
        post1.setTitolo("Guida fotografica: i 5 posti più belli di Vieste");
        post1.setContenuto("Ho passato 2 settimane a Vieste quest'estate e ho scattato centinaia di foto. Ecco i miei 5 posti preferiti: 1) Il Pizzomunno all'alba, 2) Il trabucco al tramonto, 3) Il centro storico la sera, 4) La spiaggia di Scialmarino di mattina, 5) Il porto vecchio.");
        post1.setDataCreazione(LocalDateTime.now().minusDays(15));
        PostCommunity savedPost1 = postCommunityRepository.save(post1);

        PostCommunity post2 = new PostCommunity();
        post2.setAutore(v3);
        post2.setCitta(peschici);
        post2.setTitolo("Peschici con bambini: tutto quello che dovete sapere");
        post2.setContenuto("Ho portato i miei due bambini (5 e 8 anni) a Peschici per la prima volta. Vi racconto cosa abbiamo fatto, dove abbiamo mangiato e quali spiagge preferire con i piccoli. Spoiler: è stata la vacanza più bella di sempre!");
        post2.setDataCreazione(LocalDateTime.now().minusDays(8));
        PostCommunity savedPost2 = postCommunityRepository.save(post2);

        PostCommunity post3 = new PostCommunity();
        post3.setAutore(v8);
        post3.setCitta(monteSantAngelo);
        post3.setTitolo("Monte Sant'Angelo: storia, fede e gastronomia");
        post3.setContenuto("Sono pugliese ma non avevo mai visitato Monte Sant'Angelo. Questo weekend ho colmato la lacuna. Il Santuario di San Michele è impressionante, il centro storico medievale è bellissimo, e i funghi cardoncelli di qui sono i migliori che abbia mai assaggiato.");
        post3.setDataCreazione(LocalDateTime.now().minusDays(5));
        PostCommunity savedPost3 = postCommunityRepository.save(post3);

        PostCommunity post4 = new PostCommunity();
        post4.setAutore(v1);
        post4.setCitta(vicoDelGargano);
        post4.setTitolo("Prodotti tipici del Gargano: dove acquistarli");
        post4.setContenuto("Se volete portare a casa qualcosa di autentico dal Gargano, vi consiglio: 1) Olio extravergine di oliva di Vico del Gargano (le olive nere locali sono uniche), 2) Miele di agrumi del Gargano, 3) Capocollo di Martina Franca, 4) Vino Nero di Troia. La Bottega del Gargano a Vico li ha tutti!");
        post4.setDataCreazione(LocalDateTime.now().minusDays(3));
        PostCommunity savedPost4 = postCommunityRepository.save(post4);

        // ============================================================
        // COMMENTI AI POST
        // ============================================================
        saveComment(v2, savedPost1, "Bellissimo post! Concordo al 100% sul Pizzomunno all'alba, è magico.");
        saveComment(v5, savedPost1, "Ho aggiunto tutti questi posti alla mia lista! Vado a Vieste il mese prossimo.");
        saveComment(v7, savedPost1, "Per la foto del trabucco aspettate l'ora d'oro, la luce è perfetta!");
        saveComment(v4, savedPost2, "Ottima guida! Anche noi abbiamo portato i bambini e hanno adorato le grotte marine.");
        saveComment(v1, savedPost3, "I funghi cardoncelli di Monte Sant'Angelo sono davvero eccezionali. Ottimo post!");
        saveComment(v6, savedPost4, "Confermo la Bottega del Gargano, ottima selezione e prezzi onesti. Ci compro l'olio ogni anno.");
        saveComment(v8, savedPost4, "Aggiungerei anche le ciliegie di Vico del Gargano in stagione, sono straordinarie!");
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    private void addOrariApertura(Struttura s, LocalTime apertura, LocalTime chiusura) {
        DayOfWeek[] giorni = DayOfWeek.values();
        for (int i = 0; i < giorni.length; i++) {
            s.getOrariApertura().add(new OrarioApertura(giorni[i], apertura, chiusura, false, i));
        }
    }

    private void addOrariStagionali(Struttura s) {
        addOrariApertura(s, LocalTime.of(8, 30), LocalTime.of(20, 0));
    }

    private void addOrari(Struttura s, LocalTime mat1, LocalTime mat2, LocalTime pom1, LocalTime pom2) {
        DayOfWeek[] giorni = DayOfWeek.values();
        for (int i = 0; i < giorni.length; i++) {
            boolean chiuso = (i == 6 && pom1 == null);
            s.getOrariApertura().add(new OrarioApertura(giorni[i], mat1, pom2 != null ? pom2 : mat2, chiuso, i));
        }
    }

    private void saveFoto(Struttura struttura, String url, int posizione) {
        Foto foto = new Foto();
        foto.setStruttura(struttura);
        foto.setUrlFoto(url);
        foto.setPosizione(posizione);
        foto.setVisibile(true);
        foto.setNumeroVisite(0);
        foto.setDataCaricamento(LocalDateTime.now());
        fotoRepository.save(foto);
    }

    private void saveVal(Visitor autore, Struttura struttura, int stelle, String titolo, String commento, int giorniFA) {
        Valutazione v = new Valutazione();
        v.setAutore(autore);
        v.setStruttura(struttura);
        v.setStelle(stelle);
        v.setTitolo(titolo);
        v.setCommento(commento);
        v.setDataCreazione(LocalDateTime.now().plusDays(giorniFA));
        v.setUtileCount(0);
        valutazioneRepository.save(v);
    }

    private void savePref(Visitor autore, Struttura struttura, String nota, boolean visitato) {
        Preferito p = new Preferito();
        p.setAutore(autore);
        p.setStruttura(struttura);
        p.setNota(nota);
        p.setVisitato(visitato);
        p.setDataSalvataggio(LocalDateTime.now().minusDays((long)(Math.random() * 30)));
        preferitoRepository.save(p);
    }

    private void saveMsg(Visitor autore, Citta citta, String contenuto, Long rispostaAId) {
        Messaggio m = new Messaggio();
        m.setAutore(autore);
        m.setCitta(citta);
        m.setContenuto(contenuto);
        m.setDataCreazione(LocalDateTime.now().minusDays((long)(Math.random() * 20)));
        m.setContatoreMiPiace(0);
        m.setStato(StatoMessaggio.PUBBLICATO);
        messaggioRepository.save(m);
    }

    private void saveComment(Visitor autore, PostCommunity post, String contenuto) {
        Commento c = new Commento();
        c.setAutore(autore);
        c.setPost(post);
        c.setContenuto(contenuto);
        c.setDataCreazione(LocalDateTime.now().minusDays((long)(Math.random() * 10)));
        c.setMiPiace(0);
        commentoRepository.save(c);
    }
}
