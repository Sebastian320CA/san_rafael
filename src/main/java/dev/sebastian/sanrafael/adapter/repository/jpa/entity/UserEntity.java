package dev.sebastian.sanrafael.adapter.repository.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "user_state", nullable = false)
    private boolean userState = true;

    public UserEntity(String username, String password, String role, boolean userState) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.userState = userState;
    }
}