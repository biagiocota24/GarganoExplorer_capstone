package biagioCota.biagio.controllers;

import biagioCota.biagio.payloads.StrutturaResponse;
import biagioCota.biagio.services.StrutturaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/business")
@PreAuthorize("hasRole('BUSINESS_OWNER')")
public class BusinessController {

    private final StrutturaService strutturaService;

    public BusinessController(StrutturaService strutturaService) {
        this.strutturaService = strutturaService;
    }

    @GetMapping("/strutture/me")
    public List<StrutturaResponse> getMieStrutture(@AuthenticationPrincipal UserDetails userDetails) {
        return strutturaService.findByBusinessOwnerEmail(userDetails.getUsername())
                .stream()
                .map(StrutturaResponse::fromEntity)
                .toList();
    }
}
