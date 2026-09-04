package biagioCota.biagio.controllers;

import biagioCota.biagio.entities.User;
import biagioCota.biagio.payloads.StrutturaResponse;
import biagioCota.biagio.payloads.users.UserResponse;
import biagioCota.biagio.services.StrutturaService;
import biagioCota.biagio.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final StrutturaService strutturaService;

    public AdminController(UserService userService, StrutturaService strutturaService) {
        this.userService = userService;
        this.strutturaService = strutturaService;
    }

    @GetMapping("/users")
    public Page<UserResponse> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "entryDate") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        List<User> all = userService.findAll();
        List<UserResponse> responses = all.stream().map(UserResponse::fromEntity).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), responses.size());
        List<UserResponse> pageContent = responses.subList(start, end);

        return new PageImpl<>(pageContent, pageable, responses.size());
    }

    @PatchMapping("/users/{id}/ban")
    public UserResponse toggleBan(@PathVariable UUID id) {
        User user = userService.findById(id);
        user.setActive(!user.isActive());
        User saved = userService.save(user);
        return UserResponse.fromEntity(saved);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.delete(id);
    }

    @GetMapping("/strutture")
    public List<StrutturaResponse> getAllStrutture() {
        return strutturaService.findAll()
                .stream()
                .map(StrutturaResponse::fromEntity)
                .toList();
    }
}
