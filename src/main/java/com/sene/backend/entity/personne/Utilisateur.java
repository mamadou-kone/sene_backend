package com.sene.backend.entity.personne;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Utilisateur implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String nom;

    private String prenom;

    private String address;

    private String tel;

    private LocalDate dateInscription = LocalDate.now();

    @Column(nullable = false)
    private Boolean statutCompte = true;

    @Column(nullable = false)
    private String password; // Assurez-vous de hasher le mot de passe avant de le sauvegarder


    @Column(columnDefinition = "BYTEA") // Pour le stockage d'images dans PostgreSQL
    private byte[] image;

    @ManyToOne
    private Role role; // Relation vers le rôle de l'utilisateur

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role != null ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.getNom())) : Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return statutCompte != null && statutCompte;
    }
}
