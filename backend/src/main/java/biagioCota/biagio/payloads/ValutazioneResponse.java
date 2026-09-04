package biagioCota.biagio.payloads;

import biagioCota.biagio.entities.Valutazione;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ValutazioneResponse {

    private UUID id;
    private UUID autoreId;
    private String autoreNome;
    private UUID strutturaId;
    private String strutturaNome;
    private Integer stelle;
    private String titolo;
    private String commento;
    private LocalDateTime dataCreazione;
    private Integer utileCount;

    public static ValutazioneResponse fromEntity(Valutazione v) {
        ValutazioneResponse r = new ValutazioneResponse();
        r.id = v.getId();
        r.stelle = v.getStelle();
        r.titolo = v.getTitolo();
        r.commento = v.getCommento();
        r.dataCreazione = v.getDataCreazione();
        r.utileCount = v.getUtileCount();

        if (v.getAutore() != null) {
            r.autoreId = v.getAutore().getId();
            r.autoreNome = v.getAutore().getName() + " " + v.getAutore().getSurname();
        }

        if (v.getStruttura() != null) {
            r.strutturaId = v.getStruttura().getId();
            r.strutturaNome = v.getStruttura().getName();
        }

        return r;
    }
}
