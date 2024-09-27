package com.sene.backend.repository;

import com.sene.backend.entity.personne.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNom(String nom);
}
