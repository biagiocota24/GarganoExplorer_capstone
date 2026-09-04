package biagioCota.biagio.controllers;

import biagioCota.biagio.payloads.PreferitoAddPayload;
import biagioCota.biagio.payloads.PreferitoResponse;
import biagioCota.biagio.services.PreferitoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/preferiti")
@PreAuthorize("hasRole('VISITOR')")
public class PreferitoController {

    private final PreferitoService preferitoService;

    public PreferitoController(PreferitoService preferitoService) {
        this.preferitoService = preferitoService;
    }

    @GetMapping("/me")
    public List<PreferitoResponse> getMiei(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("📋 GET /preferiti/me — " + userDetails.getUsername());
        return preferitoService.findByEmail(userDetails.getUsername());
    }

    @PostMapping("/{strutturaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public PreferitoResponse add(@PathVariable UUID strutturaId,
                                 @RequestBody(required = false) @Valid PreferitoAddPayload payload,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("❤️ POST /preferiti/" + strutturaId + " — " + userDetails.getUsername());
        return preferitoService.addByEmail(strutturaId, payload, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable UUID id,
                       @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("💔 DELETE /preferiti/" + id + " — " + userDetails.getUsername());
        preferitoService.removeByEmail(id, userDetails.getUsername());
    }
}
