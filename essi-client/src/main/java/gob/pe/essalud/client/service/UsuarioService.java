package gob.pe.essalud.client.service;


import gob.pe.essalud.client.dto.usuario.UsuarioRegisterDto;

import java.util.Map;

public interface UsuarioService {
    Map save(UsuarioRegisterDto model, String captchaToken, boolean validarCaptcha);
    Map valid(UsuarioRegisterDto model, String captchaToken, boolean validarCaptcha);
}
