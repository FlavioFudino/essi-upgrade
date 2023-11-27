package gob.pe.essalud.trx.validators.impl;


import gob.pe.essalud.trx.common.constants.EstadoUsuario;
import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.TokenRegistroDto;
import gob.pe.essalud.trx.dto.TokenRegistroRequestDto;
import gob.pe.essalud.trx.exception.ServiceException;
import gob.pe.essalud.trx.jpa.model.TokenRegistroModel;
import gob.pe.essalud.trx.jpa.model.UsuarioModel;
import gob.pe.essalud.trx.jpa.repository.TokenRegistroRepository;
import gob.pe.essalud.trx.jpa.repository.UsuarioRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.validators.TokenRegistroValidator;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenRegistroValidatorImpl implements TokenRegistroValidator {

    private final UsuarioRepository usuarioRepository;
    private final ParametroRepository parametroMyRepository;
    private final TokenRegistroRepository tokenRegistroRepository;

    public TokenRegistroValidatorImpl(UsuarioRepository usuarioRepository,
                                      TokenRegistroRepository tokenRegistroRepository,
                                      ParametroRepository parametroMyRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRegistroRepository = tokenRegistroRepository;
        this.parametroMyRepository = parametroMyRepository;
    }

    @Override
    public void validate(TokenRegistroRequestDto request) {
        UsuarioModel model = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new ServiceException("El usuario no existe"));

        if (model.getEstado() == EstadoUsuario.ACTIVO)
            throw new ServiceException("El usuario se encuentra activado");

    }

    @Override
    public TokenRegistroDto validateActivation(TokenRegistroRequestDto request, Boolean validUser) {

        if (validUser)
            validate(request);

        // Validar existencia de código de activación
        TokenRegistroModel tokenRegistro = tokenRegistroRepository
                .findTopByTokenAndTipoAndCorreoOrderByIdTokenRegistroDesc(request.getToken(),request.getTipo(),request.getCorreo())
                .orElseThrow(() -> new ServiceException("El código no es válido"));

        boolean tokenNotMatch = !tokenRegistro.getToken().equals(request.getToken());
        if (tokenNotMatch) {
            throw new ServiceException("El código no es válido");
        }

        // Validar expiración de código de activación
        Date fechaActual = parametroMyRepository.getFecha();
        boolean isExpired = fechaActual.after(tokenRegistro.getDateExpiration());
        if (isExpired) {
            throw new ServiceException("El código de activación ha expirado");
        }

        return Util.objectToObject(TokenRegistroDto.class, tokenRegistro);
    }
}
