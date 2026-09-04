package biagioCota.biagio.services;

import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.enums.Nazionalita;
import biagioCota.biagio.exceptions.DuplicateEmailException;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.payloads.users.UserUpdatePayload;
import biagioCota.biagio.payloads.users.VisitorPayload;
import biagioCota.biagio.repositories.VisitorRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public VisitorService(VisitorRepository visitorRepository, BCryptPasswordEncoder passwordEncoder) {
        this.visitorRepository = visitorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Visitor> findAll() {
        return visitorRepository.findAll();
    }

    public Visitor findById(UUID id) {
        return visitorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor non trovato con id: " + id));
    }

    public Visitor findByEmail(String email) {
        return visitorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Visitor non trovato con email: " + email));
    }

    @Transactional
    public Visitor save(VisitorPayload payload) {
        if (visitorRepository.existsByEmail(payload.getEmail())) {
            throw new DuplicateEmailException("Email già registrata: " + payload.getEmail());
        }
        Visitor visitor = new Visitor();
        visitor.setName(payload.getName());
        visitor.setSurname(payload.getSurname());
        visitor.setEmail(payload.getEmail());
        visitor.setPassword(passwordEncoder.encode(payload.getPassword()));
        visitor.setBiografy(payload.getBiografy());
        visitor.setTelephone(payload.getTelephone());
        visitor.setAvatar(payload.getAvatar());
        visitor.setNazionalita(payload.getNazionalita());
        visitor.setEntryDate(LocalDateTime.now());
        visitor.setActive(true);
        return visitorRepository.save(visitor);
    }

    @Transactional
    public Visitor update(UUID id, VisitorPayload payload) {
        Visitor esistente = findById(id);
        esistente.setName(payload.getName());
        esistente.setSurname(payload.getSurname());
        esistente.setBiografy(payload.getBiografy());
        esistente.setTelephone(payload.getTelephone());
        esistente.setAvatar(payload.getAvatar());
        esistente.setNazionalita(payload.getNazionalita());
        return visitorRepository.save(esistente);
    }

    @Transactional
    public Visitor updateByEmail(String email, UserUpdatePayload payload) {
        Visitor visitor = findByEmail(email);
        if (payload.getName() != null) visitor.setName(payload.getName());
        if (payload.getSurname() != null) visitor.setSurname(payload.getSurname());
        if (payload.getBiografy() != null) visitor.setBiografy(payload.getBiografy());
        if (payload.getTelephone() != null) visitor.setTelephone(payload.getTelephone());
        if (payload.getAvatar() != null) visitor.setAvatar(payload.getAvatar());
        if (payload.getNazionalita() != null) visitor.setNazionalita(payload.getNazionalita());
        return visitorRepository.save(visitor);
    }

    public void delete(UUID id) {
        findById(id);
        visitorRepository.deleteById(id);
    }

    public List<Visitor> findByNazionalita(Nazionalita nazionalita) {
        return visitorRepository.findByNazionalita(nazionalita);
    }

    public List<Visitor> findByActive(boolean active) {
        return visitorRepository.findByActive(active);
    }

    public List<Visitor> search(String keyword) {
        return visitorRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(keyword, keyword);
    }

    public boolean existsByEmail(String email) {
        return visitorRepository.existsByEmail(email);
    }
}
