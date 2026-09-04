package biagioCota.biagio.services;

import biagioCota.biagio.entities.Commento;
import biagioCota.biagio.entities.PostCommunity;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.payloads.CommentoCreatePayload;
import biagioCota.biagio.repositories.CommentoRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CommentoService {

    private final CommentoRepository commentoRepository;
    private final VisitorService visitorService;
    private final PostCommunityService postCommunityService;

    public CommentoService(CommentoRepository commentoRepository,
                           VisitorService visitorService,
                           PostCommunityService postCommunityService) {
        this.commentoRepository = commentoRepository;
        this.visitorService = visitorService;
        this.postCommunityService = postCommunityService;
    }

    @Transactional(readOnly = true)
    public List<Commento> findAll() {
        return commentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Commento findById(UUID id) {
        return commentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commento non trovato con id: " + id));
    }

    @Transactional
    public Commento saveFromEmail(Long postId, CommentoCreatePayload payload, String email) {
        Visitor autore = visitorService.findByEmail(email);
        PostCommunity post = postCommunityService.findById(postId);

        Commento commento = new Commento();
        commento.setAutore(autore);
        commento.setPost(post);
        commento.setContenuto(payload.getContenuto());
        commento.setDataCreazione(LocalDateTime.now());
        commento.setMiPiace(0);

        return commentoRepository.save(commento);
    }

    @Transactional
    public void deleteIfOwnerOrAdmin(UUID id, String email, boolean isAdmin) {
        Commento commento = findById(id);
        if (!isAdmin && (commento.getAutore() == null || !commento.getAutore().getEmail().equals(email))) {
            throw new AccessDeniedException("Non sei autorizzato a eliminare questo commento");
        }
        commentoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Commento> findByAutore(Visitor autore) {
        return commentoRepository.findByAutore(autore);
    }

    @Transactional(readOnly = true)
    public List<Commento> findByAutoreId(UUID autoreId) {
        return commentoRepository.findByAutoreId(autoreId);
    }

    @Transactional(readOnly = true)
    public List<Commento> findByPost(PostCommunity post) {
        return commentoRepository.findByPost(post);
    }

    @Transactional(readOnly = true)
    public List<Commento> findByPostId(Long postId) {
        return commentoRepository.findByPostId(postId);
    }

    @Transactional(readOnly = true)
    public List<Commento> findByPostIdOrdinati(Long postId) {
        return commentoRepository.findByPostIdOrderByDataCreazioneDesc(postId);
    }

    @Transactional(readOnly = true)
    public long countByPost(Long postId) {
        return commentoRepository.countByPostId(postId);
    }
}
