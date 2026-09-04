package biagioCota.biagio.payloads.strutture;

import biagioCota.biagio.enums.TipologiaStruttura;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class StrutturaBasePayload {

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 200, message = "Il nome non può superare 200 caratteri")
    protected String name;

    @NotBlank(message = "La descrizione è obbligatoria")
    @Size(max = 1000, message = "La descrizione non può superare 1000 caratteri")
    protected String descrizione;

    @NotNull(message = "La tipologia è obbligatoria")
    protected TipologiaStruttura tipologia;

    @NotNull(message = "L'indirizzo è obbligatorio")
    @Valid
    protected IndirizzoPayload indirizzo;

    private List<String> fotoUrls;

    @NotNull(message = "L'ID città è obbligatorio")
    protected UUID cittaId;

    @NotBlank(message = "Il telefono è obbligatorio")
    @Pattern(regexp = "^[+]?[0-9]{8,15}$", message = "Numero di telefono non valido")
    protected String telefono;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Formato email non valido")
    protected String email;

    @Size(max = 500, message = "L'URL del sito non può superare 500 caratteri")
    protected String sitoWebURL;

    @NotNull(message = "Specificare se accessibile ai disabili")
    protected Boolean accessoDisabili;

    @NotNull(message = "Gli orari di apertura sono obbligatori")
    @Size(min = 1, message = "Specificare almeno un orario di apertura")
    @Valid
    protected List<OrarioAperturaPayload> orariApertura;

    protected UUID businessOwnerId;

    protected Map<String, Object> attributiAggiuntivi;
}
