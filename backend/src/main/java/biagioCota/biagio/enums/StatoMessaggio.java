package biagioCota.biagio.enums;

public enum StatoMessaggio {
    PUBBLICATO("Visibile"),
    NASCOSTO("Moderato - Nascosto"),
    ELIMINATO("Eliminato");

    private final String descrizione;

    StatoMessaggio(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
