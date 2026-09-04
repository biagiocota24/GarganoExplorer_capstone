package biagioCota.biagio.payloads;

import biagioCota.biagio.entities.Citta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CittaResponse {

    private UUID id;
    private String name;
    private String cap;
    private String provincia;

    public static CittaResponse fromEntity(Citta c) {
        CittaResponse r = new CittaResponse();
        r.id = c.getId();
        r.name = c.getName();
        r.cap = c.getCAP();
        r.provincia = c.getProvincia();
        return r;
    }
}
