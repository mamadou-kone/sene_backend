package com.sene.backend.security.configurationSecurity;

import com.sene.backend.entity.personne.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtService {

    private final String ENCRYPTION_KEY = "608f36e92dc66d97d5933f0e6371493cb4fc05b1aa8f8de64014732472303a7c";
    private final UtilisateurService utilisateurService;

    public Map<String, String> generate(String username) {
        Utilisateur utilisateur = utilisateurService.loadUserByUsername(username);
        if (utilisateur == null) {
            throw new RuntimeException("Utilisateur non trouvé");
        }
        return generateJwt(utilisateur);
    }

    public String extractUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        Claims claims = getAllClaims(token);
        return Long.parseLong(claims.get("userId", String.class));
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token JWT invalide", e);
        }
    }

    private Map<String, String> generateJwt(Utilisateur utilisateur) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000; // 30 minutes

        Map<String, Object> claims = new HashMap<>();
        claims.put("nom", utilisateur.getNom() != null ? utilisateur.getNom() : "INCONNU");
        claims.put("prenom", utilisateur.getPrenom() != null ? utilisateur.getPrenom() : "INCONNU");

        // Encode l'image en Base64 si elle n'est pas null


        claims.put("userId", utilisateur.getId() != null ? utilisateur.getId().toString() : "0");
        claims.put("role", utilisateur.getRole() != null ? utilisateur.getRole().getNom() : "UNKNOWN");
        claims.put(Claims.EXPIRATION, new Date(expirationTime));
        claims.put(Claims.SUBJECT, utilisateur.getEmail() != null ? utilisateur.getEmail() : "INCONNU");

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getEmail() != null ? utilisateur.getEmail() : "INCONNU")
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer", bearer);
    }

    private BufferedImage bytesToBufferedImage(byte[] imageBytes) {
        try {
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la conversion des bytes en BufferedImage", e);
        }
    }



    private Key getKey() {
        byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public BufferedImage decodeImageFromToken(String token) throws IOException {
        Claims claims = getAllClaims(token);
        String base64Image = claims.get("image", String.class);

        if (base64Image != null) {
            byte[] imageBytes = Base64.getDecoder().decode(base64Image);
            return ImageIO.read(new ByteArrayInputStream(imageBytes));
        }
        return null; // Ou gérer le cas où l'image est absente
    }
}