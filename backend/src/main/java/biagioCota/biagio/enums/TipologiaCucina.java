package biagioCota.biagio.enums;

public enum TipologiaCucina {

    // ========== CUCINA ITALIANA REGIONALE ==========
    PUGLIESE("Pugliese", "Specialità pugliesi: orecchiette, tiella, burrata"),
    GARGANO("Del Gargano", "Specialità locali del Gargano e della penisola"),
    CAMPANIA("Campania", "Cucina napoletana e campana"),
    SICILIA("Sicilia", "Cucina siciliana, arancini, pasta alla norma"),
    TOSCANA("Toscana", "Bistecca alla fiorentina, minestrone"),
    LIGURIA("Liguria", "Pesto, focaccia, trofie al pesto"),
    PIEMONTE("Piemonte", "Tajarin, brasato, bagna cauda"),
    EMILIA_ROMAGNA("Emilia-Romagna", "Tortellini, ragù, parmigiano"),
    VENETO("Veneto", "Risotto, polenta, bacalà mantecato"),
    LAZIO("Lazio", "Pasta alla carbonara, cacio e pepe, maritozzo"),
    UMBRIA("Umbria", "Tartufi, cinghiale, formaggi"),
    ABRUZZO("Abruzzo", "Arrosticini, caciocavallo, peperone"),
    CALABRIA("Calabria", "Piatti speziati, pesce affumicato, 'nduja"),

    // ========== PESCE E FRUTTI DI MARE ==========
    PESCE("Pesce fresco", "Specialità di pesce locale"),
    FRUTTI_DI_MARE("Frutti di mare", "Ostriche, cozze, ricci di mare"),
    PESCE_SPADA("Pesce spada", "Specialità di pesce spada"),
    BRANZINO("Branzino", "Specialità di branzino e orate"),
    POLPO_CALAMARI("Polpo e calamari", "Polpo alla pignata, totani ripieni"),
    GAMBERETTI("Gamberetti", "Specialità di gamberetti e scampi"),
    ARAGOSTA("Aragosta", "Piatti con aragosta fresca"),

    // ========== CUCINA ITALIANA FUSION ==========
    ITALIANA_MODERNA("Italiana moderna", "Reinterpretazione contemporanea della cucina italiana"),
    ITALIANA_TRADIZIONALE("Italiana tradizionale", "Ricette della tradizione italiana"),
    SLOW_FOOD("Slow Food", "Cucina a km zero, produttori locali"),

    // ========== CUCINE INTERNAZIONALI ==========
    FRANCESE("Francese", "Haute cuisine, sauces classiche"),
    SPAGNOLA("Spagnola", "Paella, tapas, gazpacho"),
    PORTOGHESE("Portoghese", "Piatti a base di baccalà e pesce"),
    GRECA("Greca", "Feta, olive, moussaka, souvlaki"),
    TURCA("Turca", "Kebab, mezze, dolci medio-orientali"),
    MEDIORIENTALE("Medio-orientale", "Hummus, falafel, cous cous"),
    MAROCCHINA("Marocchina", "Tajine, cous cous, menta"),
    GIAPPONESE("Giapponese", "Sushi, ramen, tempura"),
    CINESE("Cinese", "Dim sum, noodles, piatti agrodolci"),
    THAI("Thai", "Curry, tom yum, pad thai"),
    INDONESIANA("Indonesiana", "Soto ayam, rendang, satay"),
    INDIANA("Indiana", "Curry, tandoori, naan, biryani"),
    MESSICANA("Messicana", "Tacos, burritos, guacamole"),
    BRASILIANA("Brasiliana", "Feijoada, acarajé, açaí"),
    ARGENTINA("Argentina", "Steak, empanadas, chimichurri"),
    AMERICANA("Americana", "Burger, BBQ, costine"),
    LIBANESE("Libanese", "Hummus, tabbouleh, tabule"),

    // ========== SPECIALIZZATE ==========
    VEGETARIANA("Vegetariana", "Piatti senza carne"),
    VEGANA("Vegana", "Piatti 100% plant-based"),
    GLUTEN_FREE("Senza glutine", "Piatti adatti a celiaci"),
    SENZA_LATTOSIO("Senza lattosio", "Piatti privi di lattosio"),
    HALAL("Halal", "Cucina conforme alle regole islamiche"),
    KOSHER("Kosher", "Cucina ebraica kosher"),
    BIO_BIOLOGICO("Bio/Biologico", "Ingredienti da agricoltura biologica certificata"),

    // ========== FAST FOOD & CASUAL ==========
    PIZZA("Pizza", "Pizzeria tradizionale"),
    BURGER("Burger", "Hamburgeria artigianale"),
    PANINOTECA("Paninoteca", "Panini e sandwich"),
    STREET_FOOD("Street food", "Cibo di strada"),
    KEBAB("Kebab", "Specialità turche"),
    POKE_BOWL("Poke Bowl", "Ciotole hawaiane"),
    ACAI_BOWL("Açai Bowl", "Ciotole acai e bowl"),
    GELATO("Gelato", "Gelateria artigianale"),
    PASTICCERIA("Pasticceria", "Dolci e pasticceria"),

    // ========== SPECIALITA_LOCALI ==========
    FOCACCIA("Focaccia", "Focaccia pugliese"),
    ORECCHIETTE("Orecchiette", "Orecchiette con cime di rapa, ragù"),
    TIELLA("Tiella", "Tiella di riso e cozze"),
    BURRATA("Burrata", "Specialità di burrata locale"),
    ARROSTICINI("Arrosticini", "Spiedini di carne abruzzesi"),
    PASTA_FRESCA("Pasta fresca", "Pasta fatta in casa"),
    RAGU("Ragù", "Ragù di carne lenta cottura"),
    BRASATO("Brasato", "Brasato in umido"),

    // ========== ALTRO ==========
    FUSION("Fusion", "Cucina fusion creativa"),
    ALTRO("Altro", "Tipologia non categorizzata");

    private final String label;
    private final String descrizione;

    TipologiaCucina(String label, String descrizione) {
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
     * Restituisce l'enum dal label (es. "Pugliese" → PUGLIESE)
     */
    public static TipologiaCucina fromLabel(String label) {
        for (TipologiaCucina cucina : TipologiaCucina.values()) {
            if (cucina.label.equalsIgnoreCase(label)) {
                return cucina;
            }
        }
        throw new IllegalArgumentException("Tipologia cucina non trovata: " + label);
    }

    /**
     * Raggruppa le cucine per categoria
     */
    public String getCategoria() {
        if (this.ordinal() < PESCE.ordinal()) return "Italiana Regionale";
        if (this.ordinal() < ITALIANA_MODERNA.ordinal()) return "Pesce";
        if (this.ordinal() < FRANCESE.ordinal()) return "Italiana";
        if (this.ordinal() < VEGETARIANA.ordinal()) return "Internazionale";
        if (this.ordinal() < PIZZA.ordinal()) return "Specializzate";
        if (this.ordinal() < FOCACCIA.ordinal()) return "Fast Food";
        if (this.ordinal() < FUSION.ordinal()) return "Specialità Locali";
        return "Altro";
    }

    /**
     * Verifica se è una cucina vegetariana/vegana
     */
    public boolean isVegetariana() {
        return this == VEGETARIANA || this == VEGANA;
    }

    /**
     * Verifica se è a base di pesce
     */
    public boolean isPescatariana() {
        return this == PESCE || this == FRUTTI_DI_MARE ||
                this == PESCE_SPADA || this == BRANZINO ||
                this == POLPO_CALAMARI || this == GAMBERETTI ||
                this == ARAGOSTA;
    }

    @Override
    public String toString() {
        return label;
    }
}