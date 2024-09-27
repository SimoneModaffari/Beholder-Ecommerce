package com.example.progetto.configurations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTDecodeException;

public class JwtDecoder {

    public static DecodedJWT decodeToken(String token) {
        try {
            // Decodifica il token JWT senza validarlo
            return JWT.decode(token);
        } catch (JWTDecodeException exception) {
            // Gestione dell'eccezione nel caso il token non sia valido
            System.err.println("Errore nella decodifica del token: " + exception.getMessage());
            return null;
        }
    }

    public static String getUserEmail(String token) {
        DecodedJWT decodedJWT = decodeToken(token);

        if (decodedJWT != null) {
            // Prendi il campo 'email' dai claims del token
            return decodedJWT.getClaim("email").asString();
        }
        return null;
    }
}