package biagioCota.biagio.controllers;

import biagioCota.biagio.entities.User;
import biagioCota.biagio.entities.userSubclasses.Visitor;
import biagioCota.biagio.payloads.users.UserResponse;
import biagioCota.biagio.payloads.users.UserUpdatePayload;
import biagioCota.biagio.services.UserService;
import biagioCota.biagio.services.VisitorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final VisitorService visitorService;

    public UserController(UserService userService, VisitorService visitorService) {
        this.userService = userService;
        this.visitorService = visitorService;
    }

    @GetMapping("/me")
    public UserResponse getMe(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("📋 GET /users/me — " + userDetails.getUsername());
        User user = userService.findByEmail(userDetails.getUsername());
        return UserResponse.fromEntity(user);
    }

    @PatchMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse updateMe(@RequestBody @Valid UserUpdatePayload payload,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("✏️ PATCH /users/me — " + userDetails.getUsername());
        User updated;
        User current = userService.findByEmail(userDetails.getUsername());

        if (current instanceof Visitor) {
            updated = visitorService.updateByEmail(userDetails.getUsername(), payload);
        } else {
            // BusinessOwner e Admin: aggiorna solo i campi base
            if (payload.getName() != null) current.setName(payload.getName());
            if (payload.getSurname() != null) current.setSurname(payload.getSurname());
            if (payload.getBiografy() != null) current.setBiografy(payload.getBiografy());
            if (payload.getTelephone() != null) current.setTelephone(payload.getTelephone());
            if (payload.getAvatar() != null) current.setAvatar(payload.getAvatar());
            updated = userService.save(current);
        }

        return UserResponse.fromEntity(updated);
    }
}
