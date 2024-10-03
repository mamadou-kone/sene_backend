package com.sene.backend.security.configurationSecurity;

import com.sene.backend.entity.personne.Utilisateur;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
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
        // Assurez-vous que l'ID utilisateur est bien stocké dans le token sous la clé "userId"
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

        final Map<String, Object> claims = Map.of(
                "nom", utilisateur.getNom(),
                "userId", utilisateur.getId().toString(),  // Stocker l'ID utilisateur dans le token
                "role", utilisateur.getRole() != null ? utilisateur.getRole().getNom() : "UNKNOWN", // Ajouter le rôle
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, utilisateur.getEmail()
        );

        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(utilisateur.getEmail())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }
}
