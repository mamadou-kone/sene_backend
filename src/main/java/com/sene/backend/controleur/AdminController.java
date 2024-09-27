package com.sene.backend.controleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sene.backend.entity.personne.Admin;
import com.sene.backend.service.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Ajouter un nouvel Admin
    @PostMapping
    public ResponseEntity<Admin> ajout(@RequestParam("admin") String adminJson,
                                       @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Admin admin = objectMapper.readValue(adminJson, Admin.class);

            // Gérer l'image si elle est fournie
            if (imageFile != null && !imageFile.isEmpty()) {
                admin.setImage(imageFile.getBytes());
            }

            Admin savedAdmin = adminService.ajout(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Liste des Admins
    @GetMapping
    public ResponseEntity<List<Admin>> liste() {
        List<Admin> admins = adminService.liste();
        return ResponseEntity.ok(admins);
    }

    // Trouver un Admin par ID
    @GetMapping("/{id}")
    public ResponseEntity<Admin> trouverParId(@PathVariable Long id) {
        Optional<Admin> adminOpt = adminService.trouverParId(id);
        return adminOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Mise à jour d'un Admin
    @PutMapping("/{id}")
    public ResponseEntity<Admin> miseAJour(@PathVariable Long id, @RequestBody Admin admin) {
        Admin updatedAdmin = adminService.miseAJour(admin, id);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Suppression d'un Admin par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        adminService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}