package com.sene.backend.controleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sene.backend.entity.personne.Client;
import com.sene.backend.service.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // Ajouter un nouveau Client
    @PostMapping
    public ResponseEntity<Client> ajout(@RequestParam("client") String clientJson,
                                        @RequestParam(value = "image", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Client client = objectMapper.readValue(clientJson, Client.class);

            // Gérer l'image si elle est fournie
            if (imageFile != null && !imageFile.isEmpty()) {
                client.setImage(imageFile.getBytes());
            }

            Client savedClient = clientService.ajout(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Liste des Clients
    @GetMapping
    public ResponseEntity<List<Client>> liste() {
        List<Client> clients = clientService.liste();
        return ResponseEntity.ok(clients);
    }

    // Trouver un Client par ID
    @GetMapping("/{id}")
    public ResponseEntity<Client> trouverParId(@PathVariable Long id) {
        Optional<Client> clientOpt = clientService.trouverParId(id);
        return clientOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Mise à jour d'un Client
    @PutMapping("/{id}")
    public ResponseEntity<Client> miseAJour(@PathVariable Long id, @RequestBody Client client) {
        Client updatedClient = clientService.miseAJour(client, id);
        if (updatedClient != null) {
            return ResponseEntity.ok(updatedClient);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Suppression d'un Client par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        clientService.supprimer(id);
        return ResponseEntity.noContent().build();
    }
}