package biagioCota.biagio.enums;

public enum TipoServizio {

    // ========== SALUTE & FARMACI ==========
    FARMACIA("Farmacia", "Farmacia e medicinali"),
    OSPEDALE("Ospedale", "Ospedale e pronto soccorso"),
    CLINICA_PRIVATA("Clinica privata", "Clinica medica privata"),
    STUDIO_MEDICO("Studio medico", "Ambulatorio medico generico"),
    STUDIO_DENTISTICO("Studio dentistico", "Dentista e ortodonzia"),
    LABORATORIO_ANALISI("Laboratorio analisi", "Esami del sangue e test"),
    FISIOTERAPIA("Fisioterapia", "Centro di riabilitazione e fisioterapia"),
    VETERINARIO("Veterinario", "Clinica veterinaria"),

    // ========== TRASPORTI ==========
    TAXI("Taxi", "Servizio taxi"),
    AUTOBUS("Autobus", "Stazione/fermata autobus"),
    STAZIONE_TRENI("Stazione treni", "Stazione ferroviaria"),
    PORTO("Porto", "Porto marittimo"),
    AEROPORTO("Aeroporto", "Aeroporto internazionale"),
    NOLEGGIO_AUTO("Noleggio auto", "Car rental"),
    NOLEGGIO_SCOOTER("Noleggio scooter", "Scooter e moto a noleggio"),
    NOLEGGIO_BICICLETTE("Noleggio biciclette", "Biciclette a noleggio"),
    STAZIONE_BICICLETTE("Stazione biciclette", "Bike sharing"),
    PARCHEGGIO("Parcheggio", "Parcheggio pubblico/privato"),
    STAZIONE_SERVIZIO("Stazione servizio", "Benzina/Diesel"),
    AUTOLAVAGGIO("Autolavaggio", "Lavaggio auto"),
    OFFICINA("Officina", "Riparazione e manutenzione auto"),

    // ========== BANCHE & FINANZA ==========
    BANCA("Banca", "Istituto di credito"),
    ATM_BANCOMAT("Bancomat/ATM", "Sportello automatico prelievi"),
    UFFICIO_CAMBIO("Ufficio cambio", "Cambio valute"),
    AGENZIA_VIAGGI("Agenzia viaggi", "Biglietteria e prenotazioni"),
    UFFICIO_SCOMMESSE("Ufficio scommesse", "Lotterie e scommesse sportive"),

    // ========== POSTE & COMUNICAZIONI ==========
    POSTA("Posta", "Ufficio postale"),
    TABACCHERIA("Tabaccheria", "Sigarette e articoli da tabaccheria"),
    EDICOLA("Edicola", "Giornali, riviste e fumetti"),
    OPERATORE_TELECOM("Operatore telecom", "Negozio Tim/Vodafone/Wind/etc"),

    // ========== SERVIZI PUBBLICI ==========
    COMUNE("Comune", "Ufficio comunale"),
    CARABINIERI("Carabinieri", "Caserma carabinieri"),
    POLIZIA("Polizia", "Questura/Commissariato di polizia"),
    VIGILI_DEL_FUOCO("Vigili del fuoco", "Caserma vigili del fuoco"),
    UFFICIO_ELETTORALE("Ufficio elettorale", "Seggio elettorale"),
    UFFICIO_IMPOSTA("Ufficio imposta", "Agenzia delle entrate"),
    UFFICIO_LAVORO("Ufficio lavoro", "Centro per l'impiego"),

    // ========== TURISMO & INFO ==========
    UFFICIO_TURISMO("Ufficio turismo", "Informazioni turistiche"),
    VISITOR_CENTER("Visitor center", "Centro informazioni turistische"),
    BIGLIETTERIA_ATTRAZIONI("Biglietteria", "Biglietti per musei e attrazioni"),

    // ========== EDUCAZIONE ==========
    SCUOLA_PRIMARIA("Scuola primaria", "Scuola elementare"),
    SCUOLA_SECONDARIA("Scuola secondaria", "Scuola media/superiore"),
    UNIVERSITA("Università", "Facoltà/Campus universitario"),
    BIBLIOTECA("Biblioteca", "Biblioteca pubblica"),
    SCUOLA_LINGUA("Scuola di lingue", "Corsi di lingua"),
    CORSO_FORMAZIONE("Corso formazione", "Centro di formazione professionale"),

    // ========== SVAGO & CULTURA ==========
    MUSEO("Museo", "Museo e galleria d'arte"),
    CINEMA("Cinema", "Sala cinematografica"),
    TEATRO("Teatro", "Teatro/Auditorium"),
    DISCOTECA("Discoteca", "Locale da ballo"),
    CASINÒ("Casinò", "Gioco d'azzardo"),
    PARCO_DIVERTIMENTI("Parco divertimenti", "Parco tematico"),
    CENTRO_BENESSERE("Centro benessere", "Spa e wellness"),

    // ========== ALLOGGI ==========
    HOTEL("Hotel", "Albergo"),
    OSTELLO("Ostello", "Ostello della gioventù"),
    CAMPEGGIO("Campeggio", "Campeggio"),
    AFFITTACAMERE("Affittacamere", "Camere in affitto"),
    RESORT("Resort", "Resort vacanziero"),

    // ========== RISTORAZIONE ==========
    RISTORANTE("Ristorante", "Ristorante"),
    PIZZERIA("Pizzeria", "Pizzeria"),
    BAR_CAFFE("Bar/Caffè", "Bar o caffetteria"),
    PUB("Pub", "Pub e beer house"),
    GELATERIA("Gelateria", "Gelateria artigianale"),
    PASTICCERIA("Pasticceria", "Pasticceria e confetteria"),
    PANETTERIA("Panetteria", "Panificio"),
    MACELLERIA("Macelleria", "Macelleria"),
    PESCHERIA("Pescheria", "Pescheria"),
    FRUTTIVENDOLO("Fruttivendolo", "Negozio di frutta e verdura"),
    ALIMENTARI("Alimentari", "Negozio di generi alimentari"),
    SUPERMERCATO("Supermercato", "Grande distribuzione"),
    MERCATO("Mercato", "Mercato rionale"),

    // ========== SHOPPING ==========
    CENTRO_COMMERCIALE("Centro commerciale", "Shopping mall"),
    NEGOZIO_ABBIGLIAMENTO("Negozio di abbigliamento", "Moda"),
    NEGOZIO_SCARPE("Negozio di scarpe", "Calzature"),
    GIOIELLERIA("Gioielleria", "Gioielli e orologi"),
    NEGOZIO_ELETTRONICA("Negozio elettronica", "Tecnologia"),
    NEGOZIO_ARTICOLI_CASA("Negozio articoli casa", "Arredamento"),
    NEGOZIO_SPORT("Negozio sport", "Attrezzatura sportiva"),
    NEGOZIO_LIBRI("Libreria", "Libri"),
    NEGOZIO_MUSICA("Negozio musica", "Dischi e strumenti"),
    NEGOZIO_GIOCATTOLI("Negozio giocattoli", "Giochi per bambini"),

    // ========== SERVIZI PERSONALI ==========
    PARRUCCHERIA("Parruccheria", "Taglio capelli uomo/donna"),
    BARBIERE("Barbiere", "Barbiere"),
    SALONE_BELLEZZA("Salone bellezza", "Parrucchiere, manicure, pedicure"),
    LAVANDERIA("Lavanderia", "Lavaggio e stiratura abiti"),
    TINTORIA("Tintoria", "Tintura abiti"),
    CALZOLAIO("Calzolaio", "Riparazione scarpe"),
    FALEGNAME("Falegname", "Artigiano legno"),
    MECCANICO("Meccanico", "Officina meccanica"),
    IDRAULICO("Idraulico", "Servizio idraulico"),
    ELETTRICISTA("Elettricista", "Servizio elettrico"),

    // ========== SPORT & PALESTRE ==========
    PALESTRA("Palestra", "Sala fitness"),
    PISCINA("Piscina", "Piscina pubblica"),
    CENTRO_TENNIS("Centro tennis", "Campo da tennis"),
    CAMPO_CALCIO("Campo calcio", "Campo da calcio/calcetto"),
    CENTRO_SCI("Centro sci", "Stazione sciistica"),
    SCUOLA_EQUITAZIONE("Scuola equitazione", "Centro ippico"),

    // ========== SPORT ACQUATICI ==========
    SCUOLA_DIVING("Scuola diving", "Centro immersioni"),
    SCUOLA_SURF("Scuola surf", "Scuola di surf"),
    SCUOLA_WINDSURF("Scuola windsurf", "Scuola di windsurf"),
    NOLEGGIO_BARCHE("Noleggio barche", "Affitto barche e yacht"),
    PORTO_TURISTICO("Porto turistico", "Marina turistica"),

    // ========== ALTRO ==========
    ALTRO("Altro", "Servizio non categorizzato");

    private final String label;
    private final String descrizione;

    TipoServizio(String label, String descrizione) {
        this.label = label;
        this.descrizione = descrizione;
    }

    public String getLabel() {
        return label;
    }

    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce l'enum dal label
     */
    public static TipoServizio fromLabel(String label) {
        for (TipoServizio servizio : TipoServizio.values()) {
            if (servizio.label.equalsIgnoreCase(label)) {
                return servizio;
            }
        }
        throw new IllegalArgumentException("Tipo servizio non trovato: " + label);
    }

    /**
     * Raggruppa i servizi per categoria
     */
    public String getCategoria() {
        if (this.ordinal() < TAXI.ordinal()) return "Salute & Farmaci";
        if (this.ordinal() < BANCA.ordinal()) return "Trasporti";
        if (this.ordinal() < POSTA.ordinal()) return "Banche & Finanza";
        if (this.ordinal() < COMUNE.ordinal()) return "Poste & Comunicazioni";
        if (this.ordinal() < UFFICIO_TURISMO.ordinal()) return "Servizi Pubblici";
        if (this.ordinal() < SCUOLA_PRIMARIA.ordinal()) return "Turismo & Info";
        if (this.ordinal() < MUSEO.ordinal()) return "Educazione";
        if (this.ordinal() < HOTEL.ordinal()) return "Svago & Cultura";
        if (this.ordinal() < RISTORANTE.ordinal()) return "Alloggi";
        if (this.ordinal() < CENTRO_COMMERCIALE.ordinal()) return "Ristorazione";
        if (this.ordinal() < PARRUCCHERIA.ordinal()) return "Shopping";
        if (this.ordinal() < PALESTRA.ordinal()) return "Servizi Personali";
        if (this.ordinal() < SCUOLA_DIVING.ordinal()) return "Sport & Palestre";
        return "Sport Acquatici";
    }

    /**
     * Verifica se è un servizio essenziale
     */
    public boolean isEssenziale() {
        return this == FARMACIA || this == OSPEDALE || this == CARABINIERI ||
                this == POLIZIA || this == TAXI || this == AUTOBUS;
    }

    /**
     * Verifica se è turismo-relato
     */
    public boolean isTuristico() {
        return this == UFFICIO_TURISMO || this == VISITOR_CENTER ||
                this == MUSEO || this == CINEMA || this == TEATRO ||
                this == HOTEL || this == RISTORANTE || this == AGENZIA_VIAGGI ||
                this == BIGLIETTERIA_ATTRAZIONI;
    }

    /**
     * Verifica se è un mezzo di trasporto
     */
    public boolean isTransporto() {
        return this.getCategoria().equals("Trasporti");
    }

    @Override
    public String toString() {
        return label;
    }
}
