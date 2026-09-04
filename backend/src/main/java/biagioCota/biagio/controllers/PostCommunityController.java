package biagioCota.biagio.controllers;

import biagioCota.biagio.payloads.CommentoCreatePayload;
import biagioCota.biagio.payloads.CommentoResponse;
import biagioCota.biagio.payloads.PostCommunityCreatePayload;
import biagioCota.biagio.payloads.PostCommunityResponse;
import biagioCota.biagio.services.CommentoService;
import biagioCota.biagio.services.PostCommunityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post-community")
public class PostCommunityController {

    private final PostCommunityService postCommunityService;
    private final CommentoService commentoService;

    public PostCommunityController(PostCommunityService postCommunityService,
                                   CommentoService commentoService) {
        this.postCommunityService = postCommunityService;
        this.commentoService = commentoService;
    }

    @GetMapping
    public List<PostCommunityResponse> getByCitta(@RequestParam UUID cittaId) {
        return postCommunityService.findByCittaOrdinati(cittaId)
                .stream()
                .map(PostCommunityResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public PostCommunityResponse getById(@PathVariable Long id) {
        return PostCommunityResponse.fromEntity(postCommunityService.findById(id));
    }

    @GetMapping("/{id}/commenti")
    public List<CommentoResponse> getCommenti(@PathVariable Long id) {
        return commentoService.findByPostIdOrdinati(id)
                .stream()
                .map(CommentoResponse::fromEntity)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    public PostCommunityResponse create(@RequestBody @Valid PostCommunityCreatePayload payload,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return PostCommunityResponse.fromEntity(
                postCommunityService.saveFromEmail(payload, userDetails.getUsername())
        );
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public PostCommunityResponse update(@PathVariable Long id,
                                        @RequestBody @Valid PostCommunityCreatePayload payload,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return PostCommunityResponse.fromEntity(
                postCommunityService.update(id, payload, userDetails.getUsername())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        postCommunityService.deleteIfOwnerOrAdmin(id, userDetails.getUsername(), isAdmin);
    }

    @PostMapping("/{id}/commenti")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('VISITOR')")
    public CommentoResponse addCommento(@PathVariable Long id,
                                        @RequestBody @Valid CommentoCreatePayload payload,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        return CommentoResponse.fromEntity(
                commentoService.saveFromEmail(id, payload, userDetails.getUsername())
        );
    }

    @DeleteMapping("/{id}/commenti/{commentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void deleteCommento(@PathVariable Long id,
                               @PathVariable UUID commentoId,
                               @AuthenticationPrincipal UserDetails userDetails) {
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        commentoService.deleteIfOwnerOrAdmin(commentoId, userDetails.getUsername(), isAdmin);
    }
}
