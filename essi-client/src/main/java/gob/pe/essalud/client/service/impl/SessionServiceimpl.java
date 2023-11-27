package gob.pe.essalud.client.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import gob.pe.essalud.client.dto.UserSessionDto;
import gob.pe.essalud.client.service.SessionService;

@Service
public class SessionServiceimpl implements SessionService {
    @Override
    public UserSessionDto get() {
        return (UserSessionDto) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    @Override
    public String getNumeroDocumento() {
        return get().getNumeroDocumento();
    }

    @Override
    public String getTipoDocumento() {
        return get().getTipoDocumento();
    }
}
