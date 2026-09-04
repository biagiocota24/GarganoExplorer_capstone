package biagioCota.biagio.payloads;

import biagioCota.biagio.entities.PostCommunity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PostCommunityResponse {

    private Long id;
    private UUID autoreId;
    private String autoreNome;
    private UUID cittaId;
    private String cittaNome;
    private String titolo;
    private String contenuto;
    private LocalDateTime dataCreazione;
    private int commentCount;

    public static PostCommunityResponse fromEntity(PostCommunity p) {
        PostCommunityResponse r = new PostCommunityResponse();
        r.id = p.getId();
        if (p.getAutore() != null) {
            r.autoreId = p.getAutore().getId();
            r.autoreNome = p.getAutore().getName() + " " + p.getAutore().getSurname();
        }
        if (p.getCitta() != null) {
            r.cittaId = p.getCitta().getId();
            r.cittaNome = p.getCitta().getName();
        }
        r.titolo = p.getTitolo();
        r.contenuto = p.getContenuto();
        r.dataCreazione = p.getDataCreazione();
        r.commentCount = p.getCommenti() != null ? p.getCommenti().size() : 0;
        return r;
    }
}
