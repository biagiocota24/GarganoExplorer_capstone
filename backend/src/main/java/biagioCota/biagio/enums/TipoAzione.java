package biagioCota.biagio.enums;

public enum TipoAzione {
    SOSPENSIONE_UTENTE("Sospensione utente"),
    RIATTIVAZIONE_UTENTE("Riattivazione utente"),
    ELIMINAZIONE_ACCOUNT("Eliminazione account"),

    // Contenuti
    ELIMINAZIONE_STRUTTURA("Eliminazione struttura"),
    ELIMINAZIONE_MESSAGGIO("Eliminazione messaggio"),
    ELIMINAZIONE_COMMENTO("Eliminazione commento"),
    ELIMINAZIONE_VALUTAZIONE("Eliminazione valutazione"),

    // Moderazione
    MODERAZIONE_MESSAGGIO("Messaggio nascosto"),
    MODERAZIONE_POST("Post nascosto"),
    APPROVAZIONE_CONTENUTO("Approvazione contenuto"),

    // Accesso
    ACCESSO_ADMIN("Accesso admin"),
    CAMBIO_PERMESSI("Cambio permessi admin"),

    // Analytics
    VISUALIZZAZIONE_ANALYTICS("Visualizzazione analytics"),
    ESPORTAZIONE_DATI("Esportazione dati");

    private final String descrizione;

    TipoAzione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }
}
