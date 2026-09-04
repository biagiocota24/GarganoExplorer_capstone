package biagioCota.biagio.payloads;

import biagioCota.biagio.entities.Messaggio;
import biagioCota.biagio.enums.StatoMessaggio;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MessaggioResponse {

    private Long id;
    private UUID autoreId;
    private String autoreNome;
    private UUID cittaId;
    private String cittaNome;
    private String contenuto;
    private String fotoUrl;
    private LocalDateTime dataCreazione;
    private Integer contatoreMiPiace;
    private Long rispostaAId;
    private StatoMessaggio stato;

    public static MessaggioResponse fromEntity(Messaggio m) {
        MessaggioResponse r = new MessaggioResponse();
        r.id = m.getId();
        if (m.getAutore() != null) {
            r.autoreId = m.getAutore().getId();
            r.autoreNome = m.getAutore().getName() + " " + m.getAutore().getSurname();
        }
        if (m.getCitta() != null) {
            r.cittaId = m.getCitta().getId();
            r.cittaNome = m.getCitta().getName();
        }
        r.contenuto = m.getContenuto();
        r.fotoUrl = m.getFotoUrl();
        r.dataCreazione = m.getDataCreazione();
        r.contatoreMiPiace = m.getContatoreMiPiace();
        r.stato = m.getStato();
        if (m.getRispostaA() != null) {
            r.rispostaAId = m.getRispostaA().getId();
        }
        return r;
    }
}
