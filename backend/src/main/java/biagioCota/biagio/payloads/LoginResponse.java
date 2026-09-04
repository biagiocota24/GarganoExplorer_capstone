package biagioCota.biagio.payloads;

import biagioCota.biagio.payloads.users.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    @JsonIgnore
    private String token;
    private String role;
    private UserResponse user;
}
