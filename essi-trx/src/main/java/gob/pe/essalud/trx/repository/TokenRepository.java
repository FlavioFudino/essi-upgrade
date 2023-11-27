package gob.pe.essalud.trx.repository;


import gob.pe.essalud.trx.dto.NotificacionTokenDto;
import gob.pe.essalud.trx.dto.PacienteTokenDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository {
    void insTokenUsuario(PacienteTokenDto pacienteTokenDto);

    void updTokenUsuario(PacienteTokenDto pacienteTokenDto);

    String getTokenUsuario(PacienteTokenDto pacienteTokenDto);

    List<NotificacionTokenDto> notificacionList();
}