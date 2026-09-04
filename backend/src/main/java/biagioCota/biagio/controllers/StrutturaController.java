package biagioCota.biagio.controllers;

import biagioCota.biagio.entities.Struttura;
import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.enums.TipologiaStruttura;
import biagioCota.biagio.payloads.StrutturaResponse;
import biagioCota.biagio.payloads.ValutazioneResponse;
import biagioCota.biagio.payloads.strutture.StrutturaCreatePayload;
import biagioCota.biagio.services.FotoService;
import biagioCota.biagio.services.StrutturaService;
import biagioCota.biagio.services.UserService;
import biagioCota.biagio.services.ValutazioneService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/strutture")
public class StrutturaController {

    private final StrutturaService strutturaService;
    private final ValutazioneService valutazioneService;
    private final FotoService fotoService;
    private final UserService userService;

    public StrutturaController(StrutturaService strutturaService,
                               ValutazioneService valutazioneService,
                               FotoService fotoService,
                               UserService userService) {
        this.strutturaService = strutturaService;
        this.valutazioneService = valutazioneService;
        this.fotoService = fotoService;
        this.userService = userService;
    }

    @GetMapping
    public Page<StrutturaResponse> getAll(
            @RequestParam(required = false) TipologiaStruttura tipologia,
            @RequestParam(required = false) UUID cittaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Struttura> strutture;

        if (tipologia != null && cittaId != null) {
            strutture = strutturaService.findByTipologiaAndCittaId(tipologia, cittaId, pageable);
        } else if (tipologia != null) {
            strutture = strutturaService.findByTipologia(tipologia, pageable);
        } else if (cittaId != null) {
            strutture = strutturaService.findByCittaId(cittaId, pageable);
        } else {
            strutture = strutturaService.findAll(pageable);
        }

        return strutture.map(StrutturaResponse::fromEntity);
    }

    @GetMapping("/{id}")
    public StrutturaResponse getById(@PathVariable UUID id) {
        return StrutturaResponse.fromEntity(strutturaService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public List<StrutturaResponse> getByUserId(@PathVariable UUID userId) {
        return strutturaService.findByBusinessOwnerId(userId).stream()
                .map(StrutturaResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}/valutazioni")
    public List<ValutazioneResponse> getValutazioni(@PathVariable UUID id) {
        strutturaService.findById(id);
        return valutazioneService.findByStrutturaId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('BUSINESS_OWNER')")
    public StrutturaResponse create(@RequestBody @Valid StrutturaCreatePayload payload,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        Struttura struttura = strutturaService.createFromEmail(payload, userDetails.getUsername());
        return StrutturaResponse.fromEntity(struttura);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('BUSINESS_OWNER') or hasRole('ADMIN')")
    public StrutturaResponse update(@PathVariable UUID id,
                                    @RequestBody @Valid StrutturaCreatePayload payload,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        Struttura struttura;
        boolean isAdmin = isAdmin(userDetails);
        if (isAdmin) {
            struttura = strutturaService.updateAsAdmin(id, payload);
        } else {
            struttura = strutturaService.updateFromEmail(id, payload, userDetails.getUsername());
        }
        return StrutturaResponse.fromEntity(struttura);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('BUSINESS_OWNER') or hasRole('ADMIN')")
    public void delete(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails) {
        if (isAdmin(userDetails)) {
            strutturaService.delete(id);
        } else {
            strutturaService.deleteIfOwner(id, userDetails.getUsername());
        }
    }

    @PostMapping(value = "/{id}/foto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('BUSINESS_OWNER') or hasRole('ADMIN')")
    public StrutturaResponse uploadFoto(@PathVariable UUID id,
                                        @RequestParam("file") MultipartFile file,
                                        @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        var currentUser = userService.findByEmail(userDetails.getUsername());
        fotoService.uploadForStruttura(id, file, currentUser.getId());
        return StrutturaResponse.fromEntity(strutturaService.findById(id));
    }

    @DeleteMapping("/{id}/foto/{fotoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('BUSINESS_OWNER') or hasRole('ADMIN')")
    public void deleteFoto(@PathVariable UUID id,
                           @PathVariable Long fotoId,
                           @AuthenticationPrincipal UserDetails userDetails) {
        if (isAdmin(userDetails)) {
            fotoService.delete(fotoId);
        } else {
            fotoService.deleteFromStruttura(id, fotoId, userDetails.getUsername());
        }
    }

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}
