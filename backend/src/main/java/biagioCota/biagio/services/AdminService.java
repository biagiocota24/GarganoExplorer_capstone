package biagioCota.biagio.services;

import biagioCota.biagio.entities.userSubclasses.Admin;
import biagioCota.biagio.exceptions.DuplicateEmailException;
import biagioCota.biagio.payloads.users.AdminPayload;
import biagioCota.biagio.repositories.AdminRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Admin findById(UUID id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin non trovato con id: " + id));
    }

    public Admin save(AdminPayload payload) {
        if (adminRepository.existsByEmail(payload.getEmail())) {
            throw new DuplicateEmailException("Email già registrata: " + payload.getEmail());
        }
        Admin admin = new Admin();
        admin.setName(payload.getName());
        admin.setSurname(payload.getSurname());
        admin.setEmail(payload.getEmail());
        admin.setPassword(passwordEncoder.encode(payload.getPassword()));
        admin.setBiografy(payload.getBiografy());
        admin.setTelephone(payload.getTelephone());
        admin.setAvatar(payload.getAvatar());
        admin.setDataAssunzione(payload.getDataAssunzione());
        admin.setEntryDate(LocalDateTime.now());
        admin.setActive(true);
        return adminRepository.save(admin);
    }

    public Admin update(UUID id, AdminPayload payload) {
        Admin esistente = findById(id);
        esistente.setName(payload.getName());
        esistente.setSurname(payload.getSurname());
        esistente.setBiografy(payload.getBiografy());
        esistente.setTelephone(payload.getTelephone());
        esistente.setAvatar(payload.getAvatar());
        esistente.setDataAssunzione(payload.getDataAssunzione());
        return adminRepository.save(esistente);
    }

    public void delete(UUID id) {
        findById(id);
        adminRepository.deleteById(id);
    }

    public Admin findByEmail(String email) {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Admin non trovato con email: " + email));
    }

    public List<Admin> findByActive(boolean active) {
        return adminRepository.findByActive(active);
    }

    public List<Admin> findAssuntiPrimaDel(LocalDate data) {
        return adminRepository.findByDataAssunzioneBefore(data);
    }

    public List<Admin> findAssuntiFra(LocalDate from, LocalDate to) {
        return adminRepository.findByDataAssunzioneBetween(from, to);
    }

    public List<Admin> search(String keyword) {
        return adminRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(keyword, keyword);
    }

    public boolean existsByEmail(String email) {
        return adminRepository.existsByEmail(email);
    }
}
