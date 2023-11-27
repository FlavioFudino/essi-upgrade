package gob.pe.essalud.client.service;

import gob.pe.essalud.client.dto.UserSessionDto;

public interface SessionService {
    UserSessionDto get();
    String getNumeroDocumento();
    String getTipoDocumento();
}
