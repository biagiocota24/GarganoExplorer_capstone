package biagioCota.biagio.enums;

public enum TipologiaStruttura {

    SPIAGGIA("Spiaggia", "Spiaggia e zona balneare" ,  "https://res.cloudinary.com/csamygr3/image/upload/v1787674125/SPIAGGIA_zw1q29.jpg"),
    RISTORANTE("Ristorante", "Ristorante e strutture ristorative" , "https://res.cloudinary.com/csamygr3/image/upload/v1787674518/RISTORANTE_uy92xq.jpg"),
    HOTEL("Hotel", "Albergo e struttura ricettiva","https://res.cloudinary.com/csamygr3/image/upload/v1787674579/HOTEL_tpgmnt.jpg"),
    TRASPORTO("Trasporto", "Servizio di trasporto e noleggio","https://res.cloudinary.com/csamygr3/image/upload/v1787675121/TRASPORTI_osww3y.jpg"),
    NEGOZIO("Negozio", "Negozio e attività commerciale","https://res.cloudinary.com/csamygr3/image/upload/v1787675598/NEGOZIO_uy46ia.jpg"),
    MUSEO("Museo", "Museo e luogo culturale","https://res.cloudinary.com/csamygr3/image/upload/v1787675616/MUSEO_vn5pu5.webp"),
    STABILIMENTO_BALNEARE("Stabilimento balneare", "lido con servizi" , "https://res.cloudinary.com/csamygr3/image/upload/v1787675639/SPIAGGIA_ATTREZZATA_h4ijqk.jpg"),
    AREA_NATURALE("Area Naturale", "Parco, riserva naturale, sentiero" , "https://res.cloudinary.com/csamygr3/image/upload/v1787675721/AREA_NATURALE_sl250z.jpg"),
    ATTIVITA_ACQUA("Attività Acqua", "Centro diving, windsurf, sport acquatici" , "https://res.cloudinary.com/csamygr3/image/upload/v1787675742/ATTIVITA_ACQUA_hoilrw.webp"),
    BAR_CAFFE("Bar e Caffè", "Bar, caffetteria, gelateria" ,"https://res.cloudinary.com/csamygr3/image/upload/v1787675765/BAR_CAFFE_rhkuia.jpg"),
    AGRITURISMO("Agriturismo", "Struttura agraria con ristorazione" , "https://res.cloudinary.com/csamygr3/image/upload/v1787675787/AGRITURISMO_yjuf0n.jpg");

    private final String label;
    private final String descrizione;
    private final String url;

    TipologiaStruttura(String label, String descrizione , String url) {
        this.label = label;
        this.descrizione = descrizione;
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getUrl() {
        return url;
    }

    /**
     * Restituisce l'enum dal label (es. "Spiaggia" → SPIAGGIA)
     */
    public static TipologiaStruttura fromLabel(String label) {
        for (TipologiaStruttura tipo : TipologiaStruttura.values()) {
            if (tipo.label.equalsIgnoreCase(label)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipologia non trovata: " + label);
    }
}
