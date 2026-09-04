package biagioCota.biagio.controllers;

import biagioCota.biagio.payloads.ValutazioneCreatePayload;
import biagioCota.biagio.payloads.ValutazioneResponse;
import biagioCota.biagio.services.ValutazioneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/valutazioni")
public class ValutazioneController {

    private final ValutazioneService valutazioneService;

    public ValutazioneController(ValutazioneService valutazioneService) {
        this.valutazioneService = valutazioneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('VISITOR')")
    public ValutazioneResponse create(@RequestBody @Valid ValutazioneCreatePayload payload,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("⭐ POST /valutazioni — autore=" + userDetails.getUsername());
        return valutazioneService.createFromEmail(payload, userDetails.getUsername());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('VISITOR')")
    public ValutazioneResponse update(@PathVariable UUID id,
                                      @RequestBody @Valid ValutazioneCreatePayload payload,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("✏️ PATCH /valutazioni/" + id + " — autore=" + userDetails.getUsername());
        return valutazioneService.update(id, payload, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('VISITOR') or hasRole('ADMIN')")
    public void delete(@PathVariable UUID id,
                       @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("🗑️ DELETE /valutazioni/" + id + " — autore=" + userDetails.getUsername());
        valutazioneService.delete(id, userDetails.getUsername());
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('VISITOR')")
    public List<ValutazioneResponse> getMie(@AuthenticationPrincipal UserDetails userDetails) {
        return valutazioneService.findByEmail(userDetails.getUsername());
    }
}
