package biagioCota.biagio.enums;

public enum TipoMerce {

    // ========== MODA & ACCESSORI ==========
    ABBIGLIAMENTO("Abbigliamento", "Negozio di abbigliamento uomo/donna/bambino"),
    ABBIGLIAMENTO_MARE("Abbigliamento mare", "Costumi, caftani, pareo, beachwear"),
    CALZATURE("Calzature", "Scarpe, sneaker, sandali"),
    BORSE_ZAINI("Borse e zaini", "Borse a mano, zaini da viaggio"),
    GIOIELLERIA("Gioielleria", "Gioielli, orologi, accessori preziosi"),
    BIGIOTTERIA("Bigiotteria", "Gioielli fashion, accessori"),
    OCCHIALI("Occhiali", "Occhiali da sole, da vista, sportivi"),
    SCIARPE_PASHMINA("Sciarpe e pashmina", "Sciarpe, pashmina, stole"),
    CAPPELLI_BERRETTI("Cappelli e berretti", "Cappellini, berretti, visiere"),

    // ========== ARTICOLI SPIAGGIA & SPORT ==========
    ARTICOLI_SPIAGGIA("Articoli spiaggia", "Teli mare, ciabatte, cuscini gonfiabili"),
    ATTREZZATURA_NUOTO("Attrezzatura nuoto", "Pinne, maschere, boccagli, snorkel"),
    ATTREZZATURA_IMMERSIONI("Attrezzatura immersioni", "Mute, bombole, computer subacquei"),
    ATTREZZATURA_SURF("Attrezzatura surf", "Tavole, mute, protezioni"),
    BICICLETTE("Biciclette", "Bici da strada, MTB, city bike"),
    ATTREZZATURA_TREKKING("Attrezzatura trekking", "Zaini, bastoni, scarponi"),
    ATTREZZATURA_CAMPEGGIO("Attrezzatura campeggio", "Tende, sacchi a pelo, fornelli"),
    ARTICOLI_SPORTIVI("Articoli sportivi", "Attrezzi fitness, palloni, racchette"),

    // ========== SOUVENIR & RICORDI ==========
    SOUVENIR_TIPICI("Souvenir tipici", "Ricordini, magliette, cartoline"),
    CERAMICA_ARTIGIANALE("Ceramica artigianale", "Piatti, vasi, oggetti in ceramica"),
    BOMBONIERE_REGALO("Bomboniere e regali", "Regali, oggettistica varia"),
    QUADRI_STAMPE("Quadri e stampe", "Poster, quadri, fotografie"),
    STATUETTE_DECOR("Statuette decorative", "Figurine, decorazioni casa"),
    HANDMADE_ARTIGIANATO("Handmade/Artigianato", "Oggetti artigianali locali"),

    // ========== PRODOTTI ENOGASTRONOMICI ==========
    SALUMERIA("Salumeria", "Insaccati, formaggi, affumicati"),
    PASTICCERIA_DOLCI("Pasticceria/Dolci", "Biscotti, dolci tipici, caramelle"),
    VINI_LIQUORI("Vini e liquori", "Vino, birra, superalcolici"),
    OLIO_EXTRAVERGINE("Olio extravergine", "Olio DOP, olio biologico"),
    CAFFE_TE("Caffè e tè", "Caffè, tè, tisane"),
    PANE_PRODOTTI_FRESCHI("Pane/Prodotti freschi", "Panetteria, prodotti del giorno"),
    PRODOTTI_BIO("Prodotti bio", "Alimenti biologici certificati"),
    MIELE_CONSERVE("Miele e conserve", "Miele, marmellate, conserve"),
    FRUTTA_VERDURA("Frutta e verdura", "Prodotti ortofrutticoli"),
    PESCE_SURGELATO("Pesce surgelato", "Pesce fresco/surgelato"),
    SPEZIE_ERBE("Spezie ed erbe", "Spezie, erbe aromatiche, insaporatori"),

    // ========== COSMETICA & BELLEZZA ==========
    COSMETICA("Cosmetica", "Trucchi, fondotinta, mascara"),
    PROFUMERIA("Profumeria", "Profumi, eau de toilette, fragranze"),
    PRODOTTI_SKINCARE("Prodotti skincare", "Creme, sieri, detergenti"),
    COSMETICA_NATURALE("Cosmetica naturale", "Prodotti bio, naturali, eco-friendly"),
    ERBORISTERIA("Erboristeria", "Integratori, tisane, rimedi naturali"),
    FARMACIA("Farmacia", "Medicinali, dispositivi medici"),

    // ========== CASA & ARREDAMENTO ==========
    ARREDAMENTO("Arredamento", "Mobili, divani, tavoli"),
    DECORAZIONI_CASA("Decorazioni casa", "Specchi, lampade, cornici"),
    BIANCHERIA_LETTO("Biancheria da letto", "Lenzuola, coperte, asciugamani"),
    ASCIUGAMANI("Asciugamani", "Asciugamani mare, da bagno"),
    OGGETTISTICA_CASA("Oggettistica casa", "Cuscini, tappeti, complementi"),
    STOVIGLIE("Stoviglie", "Piatti, bicchieri, posate"),
    PENTOLAME("Pentolame", "Pentole, padelle, utensili cucina"),
    ILLUMINAZIONE("Illuminazione", "Lampade, faretti, candele"),
    PIANTE_FIORI("Piante e fiori", "Piante da appartamento, fiori freschi"),

    // ========== LIBRI & CARTOLERIA ==========
    LIBRERIA("Libreria", "Libri, narrativa, saggi"),
    CARTOLERIA("Cartoleria", "Quaderni, penne, fogli, materiale scolastico"),
    FUMETTI_MANGA("Fumetti e manga", "Fumetti, manga, comic book"),
    LIBRI_TURISMO("Libri di turismo", "Guide turistiche, mappe"),
    MUSICALE("Negozio di musica", "Vinili, CD, strumenti musicali"),

    // ========== FOTOGRAFIA & TECNOLOGIA ==========
    FOTOGRAFIA("Fotografia", "Macchine fotografiche, accessori foto"),
    ELETTRONICA("Elettronica", "Telefoni, tablet, computer"),
    INFORMATICA("Informatica", "PC, monitor, periferiche"),
    DISPOSITIVI_WEARABLE("Wearable tech", "Smartwatch, auricolari, fitness tracker"),

    // ========== ANIMALI & PET ==========
    PET_SHOP("Pet shop", "Cibo animali, giochi, accessori"),
    ANIMALI_VIVI("Animali vivi", "Pesci, animali esotici"),

    // ========== SERVIZI SPECIFICI ==========
    LAVANDERIA("Lavanderia", "Servizio di lavaggio e stiratura"),
    PARRUCCHERIA("Parruccheria", "Servizi taglio capelli uomo/donna"),
    BARBIERE("Barbiere", "Barba e capelli uomo"),
    SALONE_BELLEZZA("Salone di bellezza", "Manicure, pedicure, trattamenti"),
    OTTICA("Ottica", "Occhiali da vista, lenti a contatto"),
    OROLOGERIA("Orologeria", "Riparazione e vendita orologi"),
    RIPARAZIONI("Riparazioni", "Riparazioni scarpe, abbigliamento, accessori"),

    // ========== NEGOZI ETNICI ==========
    NEGOZIO_CINESE("Negozio cinese", "Articoli vari cinesi e asiatici"),
    NEGOZIO_AFRICANO("Negozio africano", "Prodotti e oggetti africani"),
    NEGOZIO_INDIANO("Negozio indiano", "Spezie, tessuti, artigianato"),

    // ========== ARTICOLI VARI ==========
    MERCERIA("Merceria", "Ago, filo, bottoni, cerniere"),
    FERRAMENTA("Ferramenta", "Chiodi, viti, attrezzi manuali"),
    ARTICOLI_PULIZIA("Articoli di pulizia", "Detergenti, spugne, strofinacci"),
    GIOCATTOLI("Giocattoli", "Giochi per bambini"),
    ARTICOLI_RELIGIOSI("Articoli religiosi", "Icone, crocifissi, oggetti di culto"),
    EDICOLA("Edicola", "Giornali, riviste, fumetti"),
    TABACCHERIA("Tabaccheria", "Tabacco, sigarette, accendini"),
    LOTTERY_BETTING("Lottery/Betting", "Lotterie, scommesse sportive"),

    // ========== ALTRO ==========
    ALTRO("Altro", "Tipo di merce non categorizzata");

    private final String label;
    private final String descrizione;

    TipoMerce(String label, String descrizione) {
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
     * Restituisce l'enum dal label (es. "Abbigliamento" → ABBIGLIAMENTO)
     */
    public static TipoMerce fromLabel(String label) {
        for (TipoMerce merce : TipoMerce.values()) {
            if (merce.label.equalsIgnoreCase(label)) {
                return merce;
            }
        }
        throw new IllegalArgumentException("Tipo merce non trovato: " + label);
    }

    /**
     * Raggruppa i tipi di merce per categoria
     */
    public String getCategoria() {
        if (this.ordinal() < ARTICOLI_SPIAGGIA.ordinal()) return "Moda & Accessori";
        if (this.ordinal() < SOUVENIR_TIPICI.ordinal()) return "Articoli Sport";
        if (this.ordinal() < SALUMERIA.ordinal()) return "Souvenir & Ricordi";
        if (this.ordinal() < COSMETICA.ordinal()) return "Enogastronomico";
        if (this.ordinal() < ARREDAMENTO.ordinal()) return "Cosmetica & Bellezza";
        if (this.ordinal() < LIBRERIA.ordinal()) return "Casa & Arredamento";
        if (this.ordinal() < FOTOGRAFIA.ordinal()) return "Libri & Cartoleria";
        if (this.ordinal() < PET_SHOP.ordinal()) return "Tecnologia";
        if (this.ordinal() < LAVANDERIA.ordinal()) return "Animali & Pet";
        if (this.ordinal() < NEGOZIO_CINESE.ordinal()) return "Servizi";
        if (this.ordinal() < MERCERIA.ordinal()) return "Negozi Etnici";
        return "Articoli Vari";
    }

    /**
     * Verifica se è legato al turismo/spiaggia
     */
    public boolean isTuristico() {
        return this == ARTICOLI_SPIAGGIA || this == ABBIGLIAMENTO_MARE ||
                this == SOUVENIR_TIPICI || this == CERAMICA_ARTIGIANALE ||
                this == HANDMADE_ARTIGIANATO || this == ATTREZZATURA_IMMERSIONI;
    }

    /**
     * Verifica se è legato al cibo/enogastronomia
     */
    public boolean isEnogastronomico() {
        return this == SALUMERIA || this == PASTICCERIA_DOLCI ||
                this == VINI_LIQUORI || this == OLIO_EXTRAVERGINE ||
                this == CAFFE_TE || this == PANE_PRODOTTI_FRESCHI ||
                this == MIELE_CONSERVE || this == FRUTTA_VERDURA ||
                this == PESCE_SURGELATO || this == SPEZIE_ERBE;
    }

    /**
     * Verifica se è un servizio (non un negozio di vendita)
     */
    public boolean isServizio() {
        return this == LAVANDERIA || this == PARRUCCHERIA ||
                this == BARBIERE || this == SALONE_BELLEZZA ||
                this == OTTICA || this == OROLOGERIA ||
                this == RIPARAZIONI;
    }

    @Override
    public String toString() {
        return label;
    }
}
