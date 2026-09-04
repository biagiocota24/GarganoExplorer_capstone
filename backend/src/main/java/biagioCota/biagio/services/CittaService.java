package biagioCota.biagio.services;

import biagioCota.biagio.entities.Citta;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.payloads.CittaPayload;
import biagioCota.biagio.repositories.CittaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CittaService {

    private final CittaRepository cittaRepository;

    public CittaService(CittaRepository cittaRepository) {
        this.cittaRepository = cittaRepository;
    }

    public List<Citta> findAll() {
        return cittaRepository.findAll();
    }

    public Citta findById(UUID id) {
        return cittaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Città non trovata con id: " + id));
    }

    public Citta save(CittaPayload payload) {
        if (cittaRepository.existsByCAP(payload.getCAP())) {
            throw new RuntimeException("Esiste già una città con CAP: " + payload.getCAP());
        }
        Citta citta = new Citta();
        citta.setName(payload.getName());
        citta.setCAP(payload.getCAP());
        citta.setProvincia(payload.getProvincia());
        return cittaRepository.save(citta);
    }

    public Citta update(UUID id, CittaPayload payload) {
        Citta esistente = findById(id);
        esistente.setName(payload.getName());
        esistente.setCAP(payload.getCAP());
        esistente.setProvincia(payload.getProvincia());
        return cittaRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        cittaRepository.deleteById(id);
    }

    public Citta findByName(String name) {
        return cittaRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Città non trovata con nome: " + name));
    }

    public Citta findByCAP(String cap) {
        return cittaRepository.findByCAP(cap)
                .orElseThrow(() -> new ResourceNotFoundException("Città non trovata con CAP: " + cap));
    }

    public List<Citta> findByProvincia(String provincia) {
        return cittaRepository.findByProvincia(provincia);
    }

    public List<Citta> searchByName(String name) {
        return cittaRepository.findByNameContainingIgnoreCase(name);
    }

    public boolean existsByName(String name) {
        return cittaRepository.existsByName(name);
    }

    public boolean existsByCAP(String cap) {
        return cittaRepository.existsByCAP(cap);
    }
}
