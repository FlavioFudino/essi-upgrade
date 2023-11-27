package gob.pe.essalud.client.service.impl;

import java.util.Date;
import java.util.Map;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.essi.EssiClient;
import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.common.constants.CaptchaAction;
import gob.pe.essalud.client.common.constants.TipoDocumento;
import gob.pe.essalud.client.common.constants.TokenRegistro;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.util.DateUtil;
import gob.pe.essalud.client.common.util.DocumentUtil;
import gob.pe.essalud.client.common.util.SecurityUtil;
import gob.pe.essalud.client.dto.PacienteAseguradoDto;
import gob.pe.essalud.client.dto.PacienteAseguradoRequestDto;
import gob.pe.essalud.client.dto.PacienteDto;
import gob.pe.essalud.client.dto.TokenRegistroRequestDto;
import gob.pe.essalud.client.dto.essi.ActualizarDatosPersonaRequest;
import gob.pe.essalud.client.dto.essi.EssiResponseDto;
import gob.pe.essalud.client.dto.essi.PacienteEssiDto;
import gob.pe.essalud.client.dto.usuario.UsuarioRegisterDto;
import gob.pe.essalud.client.service.AuthService;
import gob.pe.essalud.client.service.CaptchaService;
import gob.pe.essalud.client.service.CentroService;
import gob.pe.essalud.client.service.SeguridadClienteService;
import gob.pe.essalud.client.service.ServiceException;
import gob.pe.essalud.client.service.TokenService;
import gob.pe.essalud.client.service.UsuarioService;

@Service
public class UsuarioServiceImpl extends BaseService implements UsuarioService {

    private final Validator validator;
    private final TrxClient trxClient;
    private final AuthService authService;
    private final CaptchaService captchaService;
    private final SeguridadClienteService seguridadClienteService;
    private final TokenService tokenService;
    private final EssiClient essiClient;
    private final CentroService centroService;

    private final int INTENTOS_RESTANTES_INDEFINIDO = -1;

    @Value("${registro.edad-minima}")
    private int edadMinima;

    @Value("${sms-cod-redes}")
    private String smsCodCentros;

    @Autowired
    public UsuarioServiceImpl(
            Validator validator,
            TrxClient trxClient,
            AuthService authService,
            CaptchaService captchaService,
            SeguridadClienteService seguridadClienteService,
            TokenService tokenService,
            EssiClient essiClient,
            CentroService centroService) {

        this.validator = validator;
        this.authService = authService;
        this.trxClient = trxClient;
        this.captchaService = captchaService;
        this.seguridadClienteService = seguridadClienteService;
        this.tokenService = tokenService;
        this.essiClient = essiClient;
        this.centroService = centroService;
    }

    @Override
    public Map save(UsuarioRegisterDto model, String captchaToken, boolean validarCaptcha) {
        final String NOMBRE_METODO = String.format("%s:%s","registrar",model.getNumeroDocIden());

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"), formatterHour.format(new Date()));

        if (validarCaptcha) {
            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Captcha"), captchaToken);
            captchaService.process(captchaToken, CaptchaAction.REGISTER);
        }

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Acceso en bloqueos"), model.getNumeroDocIden());
        seguridadClienteService.verificarAcceso();

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Caracteres"), model.getNumeroDocIden());
        this.validarCaracteres(model);

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Token Activacion"), model.getCodigo());
        this.validarTokenActivacion(model,validarCaptcha);

        // validar si el usuario ya fue registrado anteriormente
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Si el usuario ya existe"), model.getUsername());
        validateUsername(model.getUsername(),validarCaptcha);

        PacienteAseguradoDto pacienteAseguradoDto = getPacienteAseguradoFromDb(model.getTipoDocIden(), model.getNumeroDocIden(), model.getFecNacimiento());
        if (pacienteAseguradoDto != null) {
            model.setPrimerNombre(pacienteAseguradoDto.getNombres());
            model.setSegundoNombre(""); //Se quedo en dejar vacio
            model.setApelidoPaterno(pacienteAseguradoDto.getApePat());
            model.setApellidoMaterno(pacienteAseguradoDto.getApeMat());
            model.setCodCentro(pacienteAseguradoDto.getCodCentro());
        }
        else {
            //Si no se encuentra en la base de datos se buscara en ESSI de todas maneras

            // validar si los datos del asegurado son correctos en ESSI
            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando los datos en ESSI"), model.getUsername());
            PacienteEssiDto pacienteEssi = getPacienteEssi(model,validarCaptcha);

            model.setPrimerNombre(pacienteEssi.getPriNombre());
            model.setSegundoNombre(pacienteEssi.getSegNombre());
            model.setApelidoPaterno(pacienteEssi.getApePaterno());
            model.setApellidoMaterno(pacienteEssi.getApeMaterno());
            model.setCodCentro(pacienteEssi.getCodCentro());
        }

        // Si es tipo de documento DNI se validara el digito verificador
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando si el Caracter Verificar es Valido"), model.getCodVerificador());
        boolean isDni = model.getTipoDocIden().equals(TipoDocumento.DNI);
        if (isDni) {
            validarDigitoVerificador(model.getNumeroDocIden(), model.getCodVerificador(),validarCaptcha);
        }

        if (validarCaptcha)
            captchaService.success();

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Enviar a essi-trx"), model.toString());
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Fin"), formatterHour.format(new Date()));

        ActualizarDatosPersonaRequest reqActDatos = new ActualizarDatosPersonaRequest();
        reqActDatos.setCodTipDoc(model.getTipoDocIden());
        reqActDatos.setNumDoc(model.getNumeroDocIden());
        reqActDatos.setNumCelular(model.getNumCelular());
        reqActDatos.setEmail(model.getCorreo());
        EssiResponseDto respActDatos = essiClient.actualizarDatosPersona(reqActDatos);

        return trxClient.saveUser(model);
    }

    @Override
    public Map valid(UsuarioRegisterDto model, String captchaToken, boolean validarCaptcha) {
        final String NOMBRE_METODO = String.format("%s:%s","validarRegistrar",model.getNumeroDocIden());

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Inicio"), formatterHour.format(new Date()));

        if (validarCaptcha) {
            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Captcha"), captchaToken);
            captchaService.process(captchaToken, CaptchaAction.REGISTER);
        }

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Acceso en bloqueos"), model.getNumeroDocIden());
        seguridadClienteService.verificarAcceso();

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Caracteres"), model.getNumeroDocIden());
        this.validarCaracteres(model);

        // validar si el usuario ya fue registrado anteriormente
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Si el usuario ya existe"), model.getUsername());
        validateUsername(model.getUsername(),validarCaptcha);

        PacienteAseguradoDto pacienteAseguradoDto = getPacienteAseguradoFromDb(model.getTipoDocIden(), model.getNumeroDocIden(), model.getFecNacimiento());

        //Si no se encuentra en la base de datos se buscara en ESSI de todas maneras
        if (pacienteAseguradoDto == null) {
            // validar si los datos del asegurado son correctos en ESSI
            this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando los datos en ESSI"), model.getUsername());
            PacienteEssiDto pacienteEssi = getPacienteEssi(model,validarCaptcha);
        }

        // Si es tipo de documento DNI se validara el digito verificador
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando si el Caracter Verificar es Valido"), model.getCodVerificador());
        boolean isDni = model.getTipoDocIden().equals(TipoDocumento.DNI);
        if (isDni) {
            validarDigitoVerificador(model.getNumeroDocIden(), model.getCodVerificador(),validarCaptcha);
        }

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Inicializando TokenRegistro"), model.getCodigo());

        TokenRegistroRequestDto tokenRequest = new TokenRegistroRequestDto();
        tokenRequest.setIdUsuario(0L);
        tokenRequest.setToken(null);
        tokenRequest.setCorreo(model.getCorreo());
        tokenRequest.setUsername(null);
        tokenRequest.setNumCelular(model.getNumCelular());

        //TODO: Ya no se esta usando la validación por SMS
        /*
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Obteniendo Centro"), pacienteEssi.getCodCentro());
        CentroDto centro = centroService.getCentro(pacienteEssi.getCodCentro());
        boolean isCentroRegistrado = (centro != null);

        String codRed = isCentroRegistrado ? centro.getCodRed() : "000";
        //codRed = "29"; //solo para test de SMS

        boolean isSmsValidation = StringUtil.isStringInMatcher(codRed, smsCodCentros);
        if (isSmsValidation) {
            //enviara mensaje de texto con el codigo de 4 digitos, 10 minutos para validar.
            tokenRequest.setTipo(TokenRegistro.CONFIRMAR_REGISTRO_SMS);
        }
        else {
            //enviara correo con el codigo de 4 digitos, 10 minutos para validar.
            tokenRequest.setTipo(TokenRegistro.CONFIRMAR_REGISTRO_EMAIL);
        }*/

        tokenRequest.setTipo(TokenRegistro.CONFIRMAR_REGISTRO_EMAIL);

        //validar si no tiene un token activo (sin expirar)
        //(esto para evitar que llenen la BD de registros)
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Validando Token Existe Activo"), model.getCodigo());

        model.setTipoConfirmacion(tokenRequest.getTipo());
        boolean resultExist = this.validarTokenExisteActivo(model, validarCaptcha);

        if (validarCaptcha && !resultExist)
            captchaService.success();

        Map tokenResult = this.tokenService.tokenRegistro(tokenRequest);

        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Enviar a essi-trx"), tokenResult.toString());
        this.loggerInfo(String.format("[%s]: %s",NOMBRE_METODO,"Fin"), formatterHour.format(new Date()));
        return tokenResult;
    }

    private void validarIntentosRestantes(String message) {
        int intentosRestantes = seguridadClienteService.obtenerIntentosRestantes();

        if (intentosRestantes == INTENTOS_RESTANTES_INDEFINIDO) {
            throw new ServiceException(message);
        }
        else {
            throw new ServiceException(String.format("%s (%s intento(s) restante(s))",message,intentosRestantes));
        }
    }

    //se valida al momento del pre-registro
    private boolean validarTokenExisteActivo(UsuarioRegisterDto model, boolean validarCaptcha) {
        //validar si ya tiene un token existente activo sin expirar
        TokenRegistroRequestDto tokenValidRequest = new TokenRegistroRequestDto();
        tokenValidRequest.setIdUsuario(0L);
        tokenValidRequest.setToken(null);
        tokenValidRequest.setCorreo(model.getCorreo());
        tokenValidRequest.setUsername(null);
        tokenValidRequest.setTipo(model.getTipoConfirmacion());
        Map tokenValidResult = this.tokenService.existeTokenActivo(tokenValidRequest);

        String sExisteTokenResult = tokenValidResult.get("data").toString();
        boolean existeTokenActivo = Boolean.parseBoolean(sExisteTokenResult);
        if (existeTokenActivo){
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            return true;
        }
        return false;
    }

    //se valida al momento de registrar
    private void validarTokenActivacion(UsuarioRegisterDto model, boolean validarCaptcha) {
        //validar codigo de activacion
        TokenRegistroRequestDto tokenValidRequest = new TokenRegistroRequestDto();
        tokenValidRequest.setIdUsuario(0L);
        tokenValidRequest.setToken(model.getCodigo());
        tokenValidRequest.setCorreo(model.getCorreo());
        tokenValidRequest.setUsername(null);
        tokenValidRequest.setTipo(model.getTipoConfirmacion());
        Map tokenValidResult = this.tokenService.validarToken(tokenValidRequest);

        String sTokenValidResult = tokenValidResult.get("data").toString();
        boolean isValidToken = Boolean.parseBoolean(sTokenValidResult);
        if (!isValidToken){
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            if (model.getTipoConfirmacion().equals(TokenRegistro.CONFIRMAR_REGISTRO_EMAIL)) {
                seguridadClienteService.incrementarIntento("Registro","Correo",model.getCorreo(),
                        "Codigo",model.getCodigo(),"El codigo de activacion es invalido");
            }
            else if (model.getTipoConfirmacion().equals(TokenRegistro.CONFIRMAR_REGISTRO_SMS)) {
                seguridadClienteService.incrementarIntento("Registro","SMS",model.getNumCelular(),
                        "Codigo",model.getCodigo(),"El codigo de activacion es invalido");
            }

            this.validarIntentosRestantes("El Codigo de activacion es invalido");
        }
    }

    private void validarCaracteres(UsuarioRegisterDto model) {
        if (!model.getTerminosCondiciones())
            throw new ServiceException("Debe aceptar los Términos y Condiciones de Uso");

        if (!model.getAutorizacionDatosPersonales())
            throw new ServiceException("Debe aceptar la Autorización para el Tratamiento de Datos Personales");

        if (!model.getNumeroDocIden().equals(model.getUsername()))
            throw new ServiceException("El Numero de Documento y el Usuario deben ser iguales");

        String sVerifyPasswordResult = SecurityUtil.verifyPassword(model.getPassword());
        if (sVerifyPasswordResult != null)
            throw new ServiceException(sVerifyPasswordResult);

        int edad = DateUtil.calculateAge(model.getFecNacimiento());
        if (edad < edadMinima)
            throw new ServiceException(String.format("Por el momento solo se pueden registrar personas mayores a %s años",edadMinima));
    }

    private void validateUsername(String username, boolean validarCaptcha) {
        this.loggerInfo("->validateUsername", "validar si el usuario ya existe.");
        ResponseDto<Boolean> response = trxClient.getUserByCode(username);
        boolean existsUser = response != null && response.getData() != null && response.getData();
        if (existsUser) {
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Registro","Usuario",username,
                    null,null,"El usuario ya existe");

            this.validarIntentosRestantes("El usuario ya existe o los datos del asegurado son incorrectos");
        }
    }

    private PacienteAseguradoDto getPacienteAseguradoFromDb(String tipoDoc, String numDoc, String fecNac) {
        this.loggerInfo("getPacienteBdAsegurado", "validar si los datos del asegurado en base de datos ".concat(formatterHour.format(new Date())));

        PacienteAseguradoRequestDto requestDto = new PacienteAseguradoRequestDto();
        requestDto.setTipoDoc(tipoDoc);
        requestDto.setNumDoc(numDoc);
        requestDto.setFecNac(fecNac);
        return trxClient.getPacienteAsegurado(requestDto);
    }

    private PacienteEssiDto getPacienteEssi(UsuarioRegisterDto model, boolean validarCaptcha) {
        this.loggerInfo("getPacienteEssi", "validar si los datos del asegurado son correctos ".concat(formatterHour.format(new Date())));

        PacienteEssiDto essiPaciente = null;

        try {
            PacienteDto request = new PacienteDto();
            request.setTipoDocIdent(model.getTipoDocIden());
            request.setNumeroDocIdent(model.getNumeroDocIden());
            request.setFechaNacimiento(model.getFecNacimiento());
            essiPaciente = authService.getPacienteEssi(request);
        }
        catch (Exception e) {
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Registro","Usuario",model.getNumeroDocIden(),
                    "FecNacimiento",model.getFecNacimiento(),"Error en los datos proporcionados, no se encontro en ESSI Paciente");

            this.validarIntentosRestantes("El usuario ya existe o los datos del asegurado son incorrectos");
        }

        if (essiPaciente == null) {
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Registro","Usuario",model.getNumeroDocIden(),
                    "FecNacimiento",model.getFecNacimiento(),"Error en los datos proporcionados, no se encontro en ESSI Paciente");

            this.validarIntentosRestantes("El usuario ya existe o los datos del asegurado son incorrectos");
        }
        return essiPaciente;
    }

    private void validarDigitoVerificador(String dni, String caracterVerificador, boolean validarCaptcha) {
        this.loggerInfo("->validarDigitoVerificador", "validar el digito verificador.");
        boolean isValid = DocumentUtil.checkCharacterVerifier(dni, caracterVerificador);
        if (!isValid) {
            if (validarCaptcha)
                captchaService.incrementClientAttempts();

            seguridadClienteService.incrementarIntento("Registro","Usuario",dni,
                    "CaracterVerificador",caracterVerificador,"Error en el digito verificador proporcionado, no es valido");

            this.validarIntentosRestantes("El usuario ya existe o los datos del asegurado son incorrectos");
        }
    }
}
