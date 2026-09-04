package biagioCota.biagio.controllers;

import biagioCota.biagio.payloads.CittaResponse;
import biagioCota.biagio.services.CittaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/citta")
public class CittaController {

    private final CittaService cittaService;

    public CittaController(CittaService cittaService) {
        this.cittaService = cittaService;
    }

    @GetMapping
    public List<CittaResponse> getAll() {
        return cittaService.findAll().stream()
                .map(CittaResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public CittaResponse getById(@PathVariable UUID id) {
        return CittaResponse.fromEntity(cittaService.findById(id));
    }
}
