package gob.pe.essalud.client.service;

import gob.pe.essalud.client.dto.TokenRegistroRequestDto;

import java.util.Map;

public interface TokenService {

    Map token(String autorization);

    Map retoken(Map token);

    Map tokenRegistro(TokenRegistroRequestDto token);

    Map tokenActivar(TokenRegistroRequestDto token);

    Map validarToken(TokenRegistroRequestDto token);

    Map existeTokenActivo(TokenRegistroRequestDto token);

    Map sendToken(Map paramInput);
}
