package gob.pe.essalud.client.service;

import java.util.List;

public interface JwtService {
    boolean isBearer(String authorization);
    String user(String authorization);
    String tipoDocumento(String authorization);
    String numeroDocumento(String authorization);
    List<String> roles(String authorization);
}
