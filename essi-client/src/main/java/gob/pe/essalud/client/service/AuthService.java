package gob.pe.essalud.client.service;

import gob.pe.essalud.client.dto.PacienteDto;
import gob.pe.essalud.client.dto.essi.PacienteEssiDto;
import gob.pe.essalud.client.dto.usuario.UsuarioRequestDto;

public interface AuthService {

    UsuarioRequestDto login(String autorization, String captchaToken, boolean validarCaptcha, boolean useCryptoAES);

    PacienteEssiDto getPacienteEssi(PacienteDto pacienteDto);

    PacienteDto getUsuario(String userName);

}