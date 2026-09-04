package biagioCota.biagio.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PreferitoPayload {

    @NotNull(message = "L'ID autore è obbligatorio")
    private UUID autoreId;

    @NotNull(message = "L'ID struttura è obbligatorio")
    private UUID strutturaId;

    @Size(max = 500, message = "La nota non può superare 500 caratteri")
    private String nota;

    @NotNull(message = "Specificare se la struttura è stata visitata")
    private Boolean visitato;
}
