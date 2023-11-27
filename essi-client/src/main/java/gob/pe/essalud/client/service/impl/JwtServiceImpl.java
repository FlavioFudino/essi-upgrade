package gob.pe.essalud.client.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import gob.pe.essalud.client.service.JwtService;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String BEARER = "Bearer ";

    // Claims key
    private static final String USER = "user";
    private static final String ROLES = "authorities";
    private static final String TIPO_DOCUMENTO = "tipo_documento";
    private static final String NUMERO_DOCUMENTO = "numero_documento";

    @Value("${config.security.oauth.jwt.key}")
    private String secretKey;

    @Override
    public boolean isBearer(String authorization) {
        return authorization != null &&
                authorization.startsWith(BEARER) &&
                authorization.split("\\.").length == 3;
    }

    @Override
    public String user(String authorization) {
        return this.verify(authorization).getClaim(USER).asString();
    }

    @Override
    public String tipoDocumento(String authorization) {
        return this.verify(authorization).getClaim(TIPO_DOCUMENTO).asString();
    }

    @Override
    public String numeroDocumento(String authorization) {
        return this.verify(authorization).getClaim(NUMERO_DOCUMENTO).asString();
    }

    private DecodedJWT verify(String authorization) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.require(algorithm)
                .build()
                .verify(authorization.substring(BEARER.length()));
    }

    @Override
    public List<String> roles(String authorization) {
        DecodedJWT jwt = this.verify(authorization);
        return Arrays.asList(jwt.getClaim(ROLES).asArray(String.class));
    }

}
