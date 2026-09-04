package biagioCota.biagio.entities;

import biagioCota.biagio.security.validator.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class User {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    @Email
    private String email;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String biografy;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private LocalDateTime entryDate;

    @Column(nullable = false)
    private boolean active;

    @Column(nullable = true)
    private String avatar;

    public User(String name, String surname, String email, String password, String biografy, String telephone, String avatar) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.biografy = biografy;
        this.telephone = telephone;
        this.entryDate = LocalDateTime.now();
        this.active = true;
        this.avatar = avatar;
    }
}
