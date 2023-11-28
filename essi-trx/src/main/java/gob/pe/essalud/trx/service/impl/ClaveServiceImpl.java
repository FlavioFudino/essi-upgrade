package gob.pe.essalud.trx.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.common.constants.EstadoUsuario;
import gob.pe.essalud.trx.common.constants.TipoPersona;
import gob.pe.essalud.trx.common.constants.TokenRegistro;
import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.ClaveChangeRequestDto;
import gob.pe.essalud.trx.dto.ClaveRecoveryRequestDto;
import gob.pe.essalud.trx.dto.ClaveRecoveryResponseDto;
import gob.pe.essalud.trx.dto.TokenRegistroRequestDto;
import gob.pe.essalud.trx.exception.ServiceException;
import gob.pe.essalud.trx.jpa.model.ContactoModel;
import gob.pe.essalud.trx.jpa.model.PersonaModel;
import gob.pe.essalud.trx.jpa.model.TokenRegistroModel;
import gob.pe.essalud.trx.jpa.model.UsuarioModel;
import gob.pe.essalud.trx.jpa.repository.ContactoRepository;
import gob.pe.essalud.trx.jpa.repository.PersonaRepository;
import gob.pe.essalud.trx.jpa.repository.TokenRegistroRepository;
import gob.pe.essalud.trx.jpa.repository.UsuarioRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.repository.TokenRegistroMyRepository;
import gob.pe.essalud.trx.service.ClaveService;
import gob.pe.essalud.trx.service.TokenRegistroService;
import gob.pe.essalud.trx.util.StringUtil;

@Service
public class ClaveServiceImpl extends BaseService implements ClaveService {

    private final TokenRegistroService tokenRegistroService;
    private final UsuarioRepository usuarioRepository;
    private final ContactoRepository contactoRepository;
    private final ParametroRepository parametroRepository;
    private final TokenRegistroRepository tokenRegistroRepository;
    private final PersonaRepository personaRepository;
    private final TokenRegistroMyRepository tokenRegistroMyRepository; 

    @Autowired
    public ClaveServiceImpl(TokenRegistroService tokenRegistroService, UsuarioRepository usuarioRepository,
                            ContactoRepository contactoRepository, ParametroRepository parametroRepository,
                            TokenRegistroRepository tokenRegistroRepository, PersonaRepository personaRepository,
                            TokenRegistroMyRepository tokenRegistroMyRepository) {
        this.tokenRegistroService = tokenRegistroService;
        this.usuarioRepository = usuarioRepository;
        this.contactoRepository = contactoRepository;
        this.parametroRepository = parametroRepository;
        this.tokenRegistroRepository = tokenRegistroRepository;
        this.personaRepository = personaRepository;
        this.tokenRegistroMyRepository = tokenRegistroMyRepository;
    }

    @Override
    @Transactional
    public ClaveRecoveryResponseDto recovery(ClaveRecoveryRequestDto model) {
        this.loggerDebug("Inicio recovery", formatterHour.format(new Date()));

        // Obtenemos el Correo por medio del paciente
        PersonaModel pacienteModel = personaRepository.getByNumeroDocIdentAndTipo(
                model.getNumeroDocIden(), TipoPersona.USUARIO);

        if (pacienteModel == null)
            throw new ServiceException("El numero de documento no se encuentra registrado");

        ContactoModel contactoModel = contactoRepository.findTopByIdPersona(pacienteModel.getIdPersona());

        if (contactoModel == null)
            throw new ServiceException("El Usuario no tiene datos de Contacto");

        if (StringUtil.isNullOrEmpty(contactoModel.getEmail()))
            throw new ServiceException("El Usuario no tiene datos de Correo Electrónico");

        //Obtenemos el Usuario
        UsuarioModel userModel = usuarioRepository.findFirstByUsername(model.getNumeroDocIden());

        boolean isUserRegistered = userModel != null;
        if (!(isUserRegistered && userModel.getEstado() == EstadoUsuario.ACTIVO))
            throw new ServiceException("El numero de documento no se encuentra registrado");

        // Generate token
        TokenRegistroRequestDto tokenRequest = new TokenRegistroRequestDto();
        tokenRequest.setCorreo(contactoModel.getEmail());
        tokenRequest.setIdUsuario(userModel.getIdUsuario());
        tokenRequest.setUsername(model.getNumeroDocIden());
        tokenRequest.setOrigin(model.getOrigin());
        tokenRequest.setTipo(TokenRegistro.RECUPERAR_CLAVE);
        tokenRegistroService.generarTokenRecovery(tokenRequest);

        ClaveRecoveryResponseDto claveRecoveryResponseDto = Util.objectToObject(ClaveRecoveryResponseDto.class, userModel);
        claveRecoveryResponseDto.setCorreo(contactoModel.getEmail());

        this.loggerDebug("Fin recovery", formatterHour.format(new Date()));
        return claveRecoveryResponseDto;
    }

    @Override
    @Transactional
    public ClaveRecoveryResponseDto save(ClaveChangeRequestDto model) {
        this.loggerDebug("Inicio save", formatterHour.format(new Date()));

        //Validamos el token
        TokenRegistroModel tokenModel = tokenRegistroRepository
                .findTopByTokenAndTipoOrderByDateCreateDesc(model.getToken(), TokenRegistro.RECUPERAR_CLAVE).orElse(null);

        if (tokenModel == null)
            throw new ServiceException("Token no está registrado.");

        if (tokenModel.isIndConfirmado())
            throw new ServiceException("El token ya fue usado.");

        Date currentDate = parametroRepository.getFecha();

        // Validar expiración del token
        boolean isExpired = currentDate.after(tokenModel.getDateExpiration());
        if (isExpired) {
            throw new ServiceException("El token ha expirado.");
        }

        //Obtenemos el Usuario
        UsuarioModel userModel = usuarioRepository.findFirstByIdUsuario(tokenModel.getIdUsuario());
        if (userModel == null)
            throw new ServiceException("Usuario no está registrado.");

        TokenRegistroRequestDto tokenRegistroRequestDto = Util.objectToObject(TokenRegistroRequestDto.class, tokenModel);
        tokenRegistroService.activar(tokenRegistroRequestDto, false);

        userModel.setDateModify(currentDate);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(6);
        userModel.setPassword(passwordEncoder.encode(model.getNuevaClave()));
        usuarioRepository.save(userModel);

        ClaveRecoveryResponseDto claveRecoveryResponseDto = Util.objectToObject(ClaveRecoveryResponseDto.class, userModel);
        claveRecoveryResponseDto.setCorreo(tokenModel.getCorreo());

        this.loggerDebug("Fin save", formatterHour.format(new Date()));
        return claveRecoveryResponseDto;
    }
}
