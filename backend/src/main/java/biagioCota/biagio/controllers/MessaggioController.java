package biagioCota.biagio.controllers;

import biagioCota.biagio.payloads.MessaggioCreatePayload;
import biagioCota.biagio.payloads.MessaggioResponse;
import biagioCota.biagio.services.MessaggioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/messaggi")
public class MessaggioController {

    private final MessaggioService messaggioService;

    public MessaggioController(MessaggioService messaggioService) {
        this.messaggioService = messaggioService;
    }

    @GetMapping
    public List<MessaggioResponse> getByCitta(@RequestParam UUID cittaId) {
        return messaggioService.findByCittaOrdinati(cittaId)
                .stream()
                .map(MessaggioResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}/risposte")
    public List<MessaggioResponse> getRisposte(@PathVariable Long id) {
        return messaggioService.findRisposteAId(id)
                .stream()
                .map(MessaggioResponse::fromEntity)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('VISITOR')")
    public MessaggioResponse create(@RequestBody @Valid MessaggioCreatePayload payload,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        return MessaggioResponse.fromEntity(
                messaggioService.saveFromEmail(payload, userDetails.getUsername())
        );
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('VISITOR')")
    public MessaggioResponse update(@PathVariable Long id,
                                    @RequestBody @Valid MessaggioCreatePayload payload,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        return MessaggioResponse.fromEntity(
                messaggioService.update(id, payload, userDetails.getUsername())
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        messaggioService.deleteIfOwnerOrAdmin(id, userDetails.getUsername(), isAdmin);
    }
}
