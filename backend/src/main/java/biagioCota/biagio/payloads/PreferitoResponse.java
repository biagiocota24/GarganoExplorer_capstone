package biagioCota.biagio.payloads;

import biagioCota.biagio.entities.Preferito;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PreferitoResponse {

    private UUID id;
    private StrutturaResponse struttura;
    private LocalDateTime dataSalvataggio;
    private String nota;
    private Boolean visitato;

    public static PreferitoResponse fromEntity(Preferito p) {
        PreferitoResponse r = new PreferitoResponse();
        r.id = p.getId();
        r.dataSalvataggio = p.getDataSalvataggio();
        r.nota = p.getNota();
        r.visitato = p.getVisitato();

        if (p.getStruttura() != null) {
            r.struttura = StrutturaResponse.fromEntity(p.getStruttura());
        }

        return r;
    }
}
