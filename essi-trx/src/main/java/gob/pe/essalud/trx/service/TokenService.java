package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.NotificacionTokenDto;
import gob.pe.essalud.trx.dto.PacienteTokenDto;

import java.util.List;

public interface TokenService {
    void sendToken(PacienteTokenDto pacienteTokenDto);

    List<NotificacionTokenDto> notificacionList();
}
