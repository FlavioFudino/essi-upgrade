package gob.pe.essalud.emailservice.service;

import gob.pe.essalud.emailservice.dto.miconsulta.RecuperarClaveMobileRequestDto;
import gob.pe.essalud.emailservice.dto.common.RecuperarClaveWebRequestDto;
import gob.pe.essalud.emailservice.dto.miconsulta.RegistrarUsuarioRequestDto;

public interface MiConsultaService {
    boolean registrarUsuario(RegistrarUsuarioRequestDto requestDto);
    boolean recuperarClaveWeb(RecuperarClaveWebRequestDto requestDto);
    boolean recuperarClaveMobile(RecuperarClaveMobileRequestDto requestDto);
}
