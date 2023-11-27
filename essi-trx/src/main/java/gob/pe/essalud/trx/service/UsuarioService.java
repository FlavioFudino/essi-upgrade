package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.*;

public interface UsuarioService {
    UsuarioDto save(UsuarioRegisterDto model);

    UsuarioOauthDto get(String usermodel);

    PacienteDto updatePerfil(UsuarioPerfilDto model);

    Boolean existVigente(String usermodel);

    UsuarioPerfilDto getPerfil(String tipDoc, String numDoc);

    PacienteDto updateBusqActiva(AseguradoRequestDto model);
}
