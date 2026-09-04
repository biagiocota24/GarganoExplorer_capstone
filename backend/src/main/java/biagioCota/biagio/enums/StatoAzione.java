package biagioCota.biagio.enums;

public enum StatoAzione {
    SUCCESSO("Azione completata con successo"),
    FALLIMENTO("Azione fallita"),
    PARZIALE("Azione parzialmente completata");

    private final String descrizione;

    StatoAzione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
