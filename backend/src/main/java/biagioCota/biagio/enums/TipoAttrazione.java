package biagioCota.biagio.enums;

public enum TipoAttrazione {

    // ========== NATURALI ==========
    SPIAGGIA_SABBIA("Spiaggia di sabbia", "Spiaggia con sabbia fine"),
    SPIAGGIA_CIOTTOLI("Spiaggia di ciottoli", "Spiaggia con ciottoli"),
    SCOGLIERA("Scogliera", "Formazioni rocciose costiere"),
    GROTTA_MARINA("Grotta marina", "Grotta accessibile dal mare"),
    INSENATURA("Insenatura", "Piccola baia protetta"),
    LAGUNA("Laguna", "Specchio d'acqua salmastra"),

    // ========== CULTURALI & STORICHE ==========
    CASTELLO("Castello", "Fortezza o castello storico"),
    CHIESA_ABBAZIA("Chiesa/Abbazia", "Luogo di culto storico"),
    ANFITEATRO("Anfiteatro", "Struttura romana"),
    RUDERI_ARCHEOLOGIA("Ruderi archeologici", "Scavi e rovine antiche"),
    TORRE_COSTIERA("Torre costiera", "Torre di avvistamento"),
    CENTRO_STORICO("Centro storico", "Nucleo medievale preservato"),
    PALAZZO_NOBILE("Palazzo nobile", "Dimora aristocratica"),

    // ========== NATURALISTICHE ==========
    BOSCO_FORESTA("Bosco/Foresta", "Area boschiva protetta"),
    PARCO_NATURALE("Parco naturale", "Area protetta con sentieri"),
    RISERVA_NATURALE("Riserva naturale", "Zona sottoposta a tutela"),
    SORGENTE_ACQUA("Sorgente d'acqua", "Fonte o cascata d'acqua dolce"),
    LAGO_ARTIFICIALE("Lago artificiale", "Bacino idrico creato dall'uomo"),
    FIUME_TORRENTE("Fiume/Torrente", "Corso d'acqua naturale"),
    AREA_UMIDA("Area umida", "Zona paludosa ricca di fauna"),

    // ========== GASTRONOMICHE ==========
    CUCINA_TIPICA_LOCALE("Cucina tipica locale", "Piatti della tradizione"),
    PRODOTTI_A_KM_ZERO("Prodotti a km zero", "Filiera corta locale"),
    VINI_LOCALI("Vini locali", "Produzione vinicola regionale"),
    OLIO_EXTRAVERGINE("Olio extravergine", "Prodotto oleario di qualità"),
    FORMAGGI_TRADIZIONALI("Formaggi tradizionali", "Caseificio locale"),
    PESCE_FRESCO("Pesce fresco", "Pescato del giorno"),

    // ========== ATTIVITA_ESPERIENZIALI ==========
    IMMERSIONI_SUBACQUEE("Immersioni subacquee", "Diving e snorkeling"),
    VELA_WINDSURF("Vela/Windsurf", "Sport acquatici"),
    ESCURSIONI_A_PIEDI("Escursioni a piedi", "Trekking e camminate"),
    MOUNTAIN_BIKE("Mountain bike", "Percorsi ciclabili"),
    PASSEGGIATE_A_CAVALLO("Passeggiate a cavallo", "Equitazione turistica"),
    KAYAK_CANOA("Kayak/Canoa", "Navigazione costiera"),
    BIRDWATCHING("Birdwatching", "Osservazione uccelli"),

    // ========== MUSICHE & SPETTACOLI ==========
    MUSICA_LIVE("Musica live", "Concerti e performance"),
    TEATRO("Teatro", "Spettacoli teatrali"),
    FESTIVAL_SAGRA("Festival/Sagra", "Evento festivo annuale"),
    CINEMA_ARENA("Cinema all'aperto", "Proiezione esterna"),

    // ========== ARTIGIANATO ==========
    CERAMICA_ARTIGIANALE("Ceramica artigianale", "Laboratorio ceramico"),
    TESSITURA_TRADIZIONALE("Tessitura tradizionale", "Arti tessili"),
    LAVORAZIONE_LEGNO("Lavorazione del legno", "Falegnameria artigianale"),
    GIOIELLERIA("Gioielleria", "Artigianato orafo"),

    // ========== RELAX & BENESSERE ==========
    SPA_TERMALE("Spa/Termale", "Acque termali e wellness"),
    YOGA_MEDITAZIONE("Yoga/Meditazione", "Ritiri spirituali"),
    MASSAGGI_TERAPIA("Massaggi/Terapia", "Trattamenti benessere"),

    // ========== FOTOGRAFIA ==========
    SCATTO_PANORAMICO("Scatto panoramico", "Punto di vista fotografico"),
    TRAMONTO("Tramonto", "Punto privilegiato per il tramonto"),
    ALBA("Alba", "Punto privilegiato per l'alba"),

    // ========== ALTRO ==========
    PUNTO_INFORMATIVO("Punto informativo", "Visitor center/info turistica"),
    MUSEO("Museo", "Museo con collezioni"),
    GALLERIA_ARTE("Galleria d'arte", "Spazio espositivo"),
    ALTRO("Altro", "Attrazione non categorizzata");

    private final String label;
    private final String descrizione;

    TipoAttrazione(String label, String descrizione) {
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
     * Restituisce l'enum dal label (es. "Spiaggia di sabbia" → SPIAGGIA_SABBIA)
     */
    public static TipoAttrazione fromLabel(String label) {
        for (TipoAttrazione tipo : TipoAttrazione.values()) {
            if (tipo.label.equalsIgnoreCase(label)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo attrazione non trovato: " + label);
    }

    /**
     * Raggruppa i tipi per categoria
     */
    public String getCategoria() {
        if (this.ordinal() < CASTELLO.ordinal()) return "Naturali";
        if (this.ordinal() < BOSCO_FORESTA.ordinal()) return "Culturali";
        if (this.ordinal() < CUCINA_TIPICA_LOCALE.ordinal()) return "Naturalistiche";
        if (this.ordinal() < IMMERSIONI_SUBACQUEE.ordinal()) return "Gastronomiche";
        if (this.ordinal() < MUSICA_LIVE.ordinal()) return "Esperienziali";
        if (this.ordinal() < CERAMICA_ARTIGIANALE.ordinal()) return "Spettacoli";
        if (this.ordinal() < SPA_TERMALE.ordinal()) return "Artigianato";
        if (this.ordinal() < SCATTO_PANORAMICO.ordinal()) return "Benessere";
        if (this.ordinal() < PUNTO_INFORMATIVO.ordinal()) return "Fotografia";
        return "Altro";
    }
}