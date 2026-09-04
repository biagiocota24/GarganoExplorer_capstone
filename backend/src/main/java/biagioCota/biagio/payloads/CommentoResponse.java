package biagioCota.biagio.payloads;

import biagioCota.biagio.entities.Commento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CommentoResponse {

    private UUID id;
    private UUID autoreId;
    private String autoreNome;
    private Long postId;
    private String contenuto;
    private LocalDateTime dataCreazione;
    private Integer miPiace;

    public static CommentoResponse fromEntity(Commento c) {
        CommentoResponse r = new CommentoResponse();
        r.id = c.getId();
        if (c.getAutore() != null) {
            r.autoreId = c.getAutore().getId();
            r.autoreNome = c.getAutore().getName() + " " + c.getAutore().getSurname();
        }
        if (c.getPost() != null) {
            r.postId = c.getPost().getId();
        }
        r.contenuto = c.getContenuto();
        r.dataCreazione = c.getDataCreazione();
        r.miPiace = c.getMiPiace();
        return r;
    }
}
