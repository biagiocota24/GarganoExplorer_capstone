package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.entities.PostCommunity;
import biagioCota.biagio.entities.User;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.payloads.PostCommunityCreatePayload;
import biagioCota.biagio.repositories.PostCommunityRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PostCommunityService {

    private final PostCommunityRepository postCommunityRepository;
    private final UserService userService;
    private final CittaService cittaService;

    public PostCommunityService(PostCommunityRepository postCommunityRepository,
                                UserService userService,
                                CittaService cittaService) {
        this.postCommunityRepository = postCommunityRepository;
        this.userService = userService;
        this.cittaService = cittaService;
    }

    @Transactional(readOnly = true)
    public List<PostCommunity> findAll() {
        return postCommunityRepository.findAll();
    }

    @Transactional(readOnly = true)
    public PostCommunity findById(Long id) {
        return postCommunityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post non trovato con id: " + id));
    }

    @Transactional
    public PostCommunity saveFromEmail(PostCommunityCreatePayload payload, String email) {
        User autore = userService.findByEmail(email);
        Citta citta = cittaService.findById(payload.getCittaId());

        PostCommunity post = new PostCommunity();
        post.setAutore(autore);
        post.setCitta(citta);
        post.setTitolo(payload.getTitolo());
        post.setContenuto(payload.getContenuto());
        post.setDataCreazione(LocalDateTime.now());

        return postCommunityRepository.save(post);
    }

    @Transactional
    public PostCommunity update(Long id, PostCommunityCreatePayload payload, String email) {
        PostCommunity post = findById(id);
        if (post.getAutore() == null || !post.getAutore().getEmail().equals(email)) {
            throw new AccessDeniedException("Non sei l'autore di questo post");
        }
        post.setTitolo(payload.getTitolo());
        post.setContenuto(payload.getContenuto());
        return postCommunityRepository.save(post);
    }

    @Transactional
    public void deleteIfOwnerOrAdmin(Long id, String email, boolean isAdmin) {
        PostCommunity post = findById(id);
        if (!isAdmin && (post.getAutore() == null || !post.getAutore().getEmail().equals(email))) {
            throw new AccessDeniedException("Non sei autorizzato a eliminare questo post");
        }
        postCommunityRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<PostCommunity> findByCitta(Citta citta) {
        return postCommunityRepository.findByCitta(citta);
    }

    @Transactional(readOnly = true)
    public List<PostCommunity> findByCittaId(UUID cittaId) {
        return postCommunityRepository.findByCittaId(cittaId);
    }

    @Transactional(readOnly = true)
    public List<PostCommunity> findByAutore(User autore) {
        return postCommunityRepository.findByAutore(autore);
    }

    @Transactional(readOnly = true)
    public List<PostCommunity> findByAutoreId(UUID autoreId) {
        return postCommunityRepository.findByAutoreId(autoreId);
    }

    @Transactional(readOnly = true)
    public List<PostCommunity> findByCittaOrdinati(UUID cittaId) {
        return postCommunityRepository.findByCittaIdOrderByDataCreazioneDesc(cittaId);
    }

    @Transactional(readOnly = true)
    public long countByCitta(UUID cittaId) {
        return postCommunityRepository.countByCittaId(cittaId);
    }

    @Transactional(readOnly = true)
    public long countByAutore(UUID autoreId) {
        return postCommunityRepository.countByAutoreId(autoreId);
    }
}
