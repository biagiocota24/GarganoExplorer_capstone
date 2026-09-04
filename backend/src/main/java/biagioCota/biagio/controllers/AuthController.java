package biagioCota.biagio.controllers;

import biagioCota.biagio.payloads.LoginPayload;
import biagioCota.biagio.payloads.LoginResponse;
import biagioCota.biagio.payloads.users.AdminPayload;
import biagioCota.biagio.payloads.users.BusinessOwnerPayload;
import biagioCota.biagio.payloads.users.UserResponse;
import biagioCota.biagio.payloads.users.VisitorPayload;
import biagioCota.biagio.security.AuthService;
import biagioCota.biagio.services.AdminService;
import biagioCota.biagio.services.BusinessOwnerService;
import biagioCota.biagio.services.VisitorService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final BusinessOwnerService businessOwnerService;
    private final VisitorService visitorService;
    private final AdminService adminService;
    private final AuthService authService;

    public AuthController(BusinessOwnerService businessOwnerService, VisitorService visitorService,
                          AdminService adminService, AuthService authService) {
        this.businessOwnerService = businessOwnerService;
        this.visitorService = visitorService;
        this.adminService = adminService;
        this.authService = authService;
    }

    @PostMapping("/register/visitor")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerVisitor(@RequestBody @Valid VisitorPayload payload) {
        return UserResponse.fromEntity(visitorService.save(payload));
    }

    @PostMapping("/register/owner")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerOwner(@RequestBody @Valid BusinessOwnerPayload payload) {
        return UserResponse.fromEntity(businessOwnerService.save(payload));
    }

    @PostMapping("/register/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerAdmin(@RequestBody @Valid AdminPayload payload) {
        return UserResponse.fromEntity(adminService.save(payload));
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody @Valid LoginPayload payload, HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(payload);

        ResponseCookie cookie = ResponseCookie.from("authToken", loginResponse.getToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return loginResponse;
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("authToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
