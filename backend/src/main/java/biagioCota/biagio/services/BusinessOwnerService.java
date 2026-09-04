package biagioCota.biagio.services;

import biagioCota.biagio.entities.userSubclasses.BusinessOwner;
import biagioCota.biagio.exceptions.DuplicateEmailException;
import biagioCota.biagio.payloads.users.BusinessOwnerPayload;
import biagioCota.biagio.repositories.BusinessOwnerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BusinessOwnerService {

    private final BusinessOwnerRepository businessOwnerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public BusinessOwnerService(BusinessOwnerRepository businessOwnerRepository,
                                BCryptPasswordEncoder passwordEncoder) {
        this.businessOwnerRepository = businessOwnerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<BusinessOwner> findAll() {
        return businessOwnerRepository.findAll();
    }

    public BusinessOwner findById(UUID id) {
        return businessOwnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BusinessOwner non trovato con id: " + id));
    }

    public BusinessOwner save(BusinessOwnerPayload payload) {
        if (businessOwnerRepository.existsByEmail(payload.getEmail())) {
            throw new DuplicateEmailException("Email già registrata: " + payload.getEmail());
        }
        BusinessOwner owner = new BusinessOwner();
        owner.setName(payload.getName());
        owner.setSurname(payload.getSurname());
        owner.setEmail(payload.getEmail());
        owner.setPassword(passwordEncoder.encode(payload.getPassword()));
        owner.setBiografy(payload.getBiografy());
        owner.setTelephone(payload.getTelephone());
        owner.setAvatar(payload.getAvatar());
        owner.setEntryDate(LocalDateTime.now());
        owner.setActive(true);
        return businessOwnerRepository.save(owner);
    }

    public BusinessOwner update(UUID id, BusinessOwnerPayload payload) {
        BusinessOwner esistente = findById(id);
        esistente.setName(payload.getName());
        esistente.setSurname(payload.getSurname());
        esistente.setBiografy(payload.getBiografy());
        esistente.setTelephone(payload.getTelephone());
        esistente.setAvatar(payload.getAvatar());
        return businessOwnerRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        businessOwnerRepository.deleteById(id);
    }

    public BusinessOwner findByEmail(String email) {
        return businessOwnerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("BusinessOwner non trovato con email: " + email));
    }

    public List<BusinessOwner> findByActive(boolean active) {
        return businessOwnerRepository.findByActive(active);
    }

    public List<BusinessOwner> search(String keyword) {
        return businessOwnerRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(keyword, keyword);
    }

    public boolean existsByEmail(String email) {
        return businessOwnerRepository.existsByEmail(email);
    }
}
