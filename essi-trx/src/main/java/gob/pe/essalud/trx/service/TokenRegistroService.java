package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.TokenRegistroDto;
import gob.pe.essalud.trx.dto.TokenRegistroRequestDto;
import gob.pe.essalud.trx.dto.TokenRequestDto;

public interface TokenRegistroService {
    TokenRegistroDto generarToken(TokenRegistroRequestDto request);

    TokenRegistroDto generarToken(TokenRegistroRequestDto request, boolean validarUsuario);

    void activar(TokenRegistroRequestDto tokenRequest, Boolean validUser);

    boolean validarToken(TokenRegistroRequestDto tokenRequest);

    boolean existeTokenActivo(TokenRegistroRequestDto tokenRequest);

    TokenRegistroDto generarTokenRecovery(TokenRegistroRequestDto request);

    TokenRequestDto getTokenRecovery(String token);
}
