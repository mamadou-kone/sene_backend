package com.sene.backend.service.services;

import com.sene.backend.entity.personne.Admin;
import com.sene.backend.entity.personne.Role;
import com.sene.backend.repository.AdminRepository;
import com.sene.backend.repository.RoleRepository;
import com.sene.backend.security.configurationSecurity.ConfigurationCryptageMotDePasse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final ConfigurationCryptageMotDePasse passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository,
                        RoleRepository roleRepository,
                        ConfigurationCryptageMotDePasse passwordEncoder) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Méthode pour initialiser l'admin par défaut



    // Ajouter un nouvel Admin
    public Admin ajout(Admin admin) {
        // Récupérer le rôle "Admin"
        Role roleAdmin = roleRepository.findByNom("Admin");
        admin.setPassword(passwordEncoder.passwordEncoder().encode(admin.getPassword()));

        if (roleAdmin == null) {
            // Si le rôle n'existe pas, le créer
            roleAdmin = new Role();
            roleAdmin.setNom("Admin");
            roleRepository.save(roleAdmin);
        }

        // Assigner le rôle à l'admin
        admin.setRole(roleAdmin);
        return adminRepository.save(admin);
    }

    // Lister tous les Admins
    public List<Admin> liste() {
        return adminRepository.findAll();
    }

    // Trouver un Admin par ID
    public Optional<Admin> trouverParId(Long id) {
        return adminRepository.findById(id);
    }

    // Mise à jour d'un Admin
    public Admin miseAJour(Admin entity, Long id) {
        Optional<Admin> existingAdmin = adminRepository.findById(id);
        if (existingAdmin.isPresent()) {
            Admin updatedAdmin = existingAdmin.get();
            updatedAdmin.setNom(entity.getNom());
            updatedAdmin.setPrenom(entity.getPrenom());
            updatedAdmin.setEmail(entity.getEmail());
            updatedAdmin.setTel(entity.getTel());
            updatedAdmin.setAddress(entity.getAddress());
            updatedAdmin.setStatutCompte(entity.getStatutCompte());
            updatedAdmin.setImage(entity.getImage()); // Gérer l'image
            return adminRepository.save(updatedAdmin);
        } else {
            return null; // Si l'admin n'existe pas
        }
    }

    // Supprimer un Admin par ID
    public void supprimer(Long id) {
        adminRepository.deleteById(id);
    }
}