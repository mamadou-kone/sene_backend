package com.sene.backend.entity.personne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sene.backend.entity.achat.Achat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Utilisateur {
    @JsonIgnore
    @OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
    private Set<Achat> achat;

}

