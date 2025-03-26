package repository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.security.Key;

public class jwt {

    // Clave secreta en bytes
    private static final byte[] SECRET_KEY_BYTES = "holamela1234567891234567891234894512".getBytes();
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_BYTES);

    public static String generateJWT(String mail) {
        long expirationTime = System.currentTimeMillis() + (24 * 60 * 60 * 1000); // 1 día de validez

        return Jwts.builder()
                .setSubject(mail) // Mail del usuario
                .setIssuedAt(new Date())
                .setExpiration(new Date(expirationTime))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // Usa la clave segura
                .compact();
    }
}
