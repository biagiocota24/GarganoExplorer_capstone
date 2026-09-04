package biagioCota.biagio.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class FotoPayload {

    @NotNull(message = "L'ID struttura è obbligatorio")
    private UUID strutturaId;

    @NotBlank(message = "L'URL della foto è obbligatorio")
    @Size(max = 500, message = "L'URL non può superare 500 caratteri")
    private String urlFoto;

    @PositiveOrZero(message = "La posizione non può essere negativa")
    private Integer posizione;

    private UUID caricataDaUserId;
}
