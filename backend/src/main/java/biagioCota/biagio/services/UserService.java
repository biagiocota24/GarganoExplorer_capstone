package biagioCota.biagio.services;

import biagioCota.biagio.entities.User;
import biagioCota.biagio.exceptions.ResourceNotFoundException;
import biagioCota.biagio.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con id: " + id));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con email: " + email));
    }

    public User findByEmailAndActive(String email, boolean active) {
        return userRepository.findByEmailAndActive(email, active)
                .orElseThrow(() -> new ResourceNotFoundException("Utente non trovato con email: " + email));
    }

    public List<User> findByActive(boolean active) {
        return userRepository.findByActive(active);
    }

    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> findBySurname(String surname) {
        return userRepository.findBySurname(surname);
    }

    public List<User> search(String keyword) {
        return userRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(keyword, keyword);
    }

    public List<User> findRegisteredBefore(LocalDateTime date) {
        return userRepository.findByEntryDateBefore(date);
    }

    public List<User> findRegisteredAfter(LocalDateTime date) {
        return userRepository.findByEntryDateAfter(date);
    }

    public List<User> findRegisteredBetween(LocalDateTime from, LocalDateTime to) {
        return userRepository.findByEntryDateBetween(from, to);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(UUID id) {
        findById(id);
        userRepository.deleteById(id);
    }
}
