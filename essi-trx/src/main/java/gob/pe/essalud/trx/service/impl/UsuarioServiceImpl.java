package gob.pe.essalud.trx.service.impl;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.common.constants.*;
import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.*;
import gob.pe.essalud.trx.exception.ServiceException;
import gob.pe.essalud.trx.jpa.model.*;
import gob.pe.essalud.trx.jpa.repository.*;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.repository.UsuarioMyRepository;
import gob.pe.essalud.trx.service.TokenRegistroService;
import gob.pe.essalud.trx.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class UsuarioServiceImpl extends BaseService implements UsuarioService {

    private final TokenRegistroService tokenRegistroService;
    private final UsuarioRepository usuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final PersonaRepository personaRepository;
    private final ParametroRepository parametroRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final UsuarioMyRepository usuarioMyRepository;
    private final ContactoRepository contactoRepository;
    private final DireccionRepository direccionRepository;
    private final PacienteFamiliarRepository pacienteFamiliarRepository;

    @Autowired
    public UsuarioServiceImpl(TokenRegistroService tokenRegistroService,
                              UsuarioRepository usuarioRepository,
                              PacienteRepository pacienteRepository,
                              ParametroRepository parametroRepository,
                              UsuarioRolRepository usuarioRolRepository,
                              UsuarioMyRepository usuarioMyRepository,
                              PersonaRepository personaRepository,
                              ContactoRepository contactoRepository,
                              DireccionRepository direccionRepository,
                              PacienteFamiliarRepository pacienteFamiliarRepository) {
        this.tokenRegistroService = tokenRegistroService;
        this.usuarioRepository = usuarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.personaRepository = personaRepository;
        this.parametroRepository = parametroRepository;
        this.usuarioRolRepository = usuarioRolRepository;
        this.usuarioMyRepository = usuarioMyRepository;
        this.contactoRepository = contactoRepository;
        this.direccionRepository = direccionRepository;
        this.pacienteFamiliarRepository = pacienteFamiliarRepository;
    }

    @Override
    @Transactional
    public UsuarioDto save(UsuarioRegisterDto model) {
        UsuarioModel user = usuarioRepository.findFirstByUsername(model.getUsername());

        boolean isUserRegistered = user != null;

        if (isUserRegistered && (user.getEstado() == EstadoUsuario.ACTIVO)) {
            throw new ServiceException("El numero de documento ya se encuentra registrado");
        }

        Date currentDate = parametroRepository.getFecha();

        // Person Register
        Integer idPersona = registerOrUpdatePersona(model, currentDate);

        // User register
        UsuarioModel usuarioModel = userRegister(idPersona, model.getUsername(), model.getPassword(), currentDate);

        //Roles
        registerRole(idPersona, isUserRegistered);

        // Patient register
        if (!isUserRegistered)
            patientRegister(idPersona, model, currentDate);

        // Contacto inicial
        saveContactoFamiliar(idPersona, model, currentDate);

        // Generate token (activar cuenta por EMAIL / SMS)
        TokenRegistroRequestDto tokenActivarReq = new TokenRegistroRequestDto();
        tokenActivarReq.setToken(model.getCodigo());
        tokenActivarReq.setCorreo(model.getCorreo());
        tokenActivarReq.setIdUsuario(usuarioModel.getIdUsuario());
        tokenActivarReq.setTipo(model.getTipoConfirmacion());
        tokenRegistroService.activar(tokenActivarReq, false);

        return Util.objectToObject(UsuarioDto.class, usuarioModel);
    }

    @Override
    public UsuarioOauthDto get(String username) {
        UsuarioModel user = usuarioRepository.findFirstByUsername(username);
        boolean isUserRegistered = user != null;
        if (!(isUserRegistered && user.getEstado() == EstadoUsuario.ACTIVO)) {
            this.loggerError("loggerError", "El usuario no se encuentra registrado o no esta activo: ".concat(username));
            throw new ServiceException("El usuario no se encuentra registrado o no esta activo");
        }
        return usuarioMyRepository.getByUsername(username);
    }

    @Override
    @Transactional
    public PacienteDto updatePerfil(UsuarioPerfilDto model) {
        PersonaModel personaModel = personaRepository
                .findByNumeroDocIdentAndTipo(model.getNumeroDocIdent(), TipoPersona.USUARIO)
                .orElseThrow(() -> new ServiceException("Persona no registrada"));

        Date fecha = parametroRepository.getFecha();
        if (model.getIndPadomi() == null)
            model.setIndPadomi(false);

        boolean hasInscription = updatePatient(personaModel.getIdPersona(), model, fecha);
        addOrUpdateLocation(personaModel.getIdPersona(), model.getDireccion(), fecha, false);
        addOrUpdateContact(personaModel.getIdPersona(), model.getContacto(), fecha, false);
        if (model.getIndPadomi() && !existContactoFamiliar(personaModel.getIdPersona()))
            throw new ServiceException("Para inscribirse a PADOMI debe registrar previamente los datos de su familiar o acompañante.");

        if (model.getIndPadomi() && !hasInscription)
            sendInscripcionPadomiEmail(model.getContacto().getEmail());

        return Util.objectToObject(PacienteDto.class, personaModel);
    }

    @Override
    @Transactional
    public PacienteDto updateBusqActiva(AseguradoRequestDto model) {
        PersonaModel personaModel = personaRepository
                .findByNumeroDocIdent(model.getNumeroDocIdent()).orElse(null);
        if (personaModel != null) {
            Date fecha = parametroRepository.getFecha();
            addOrUpdateContact(personaModel.getIdPersona(), model.getContacto(), fecha, true);

            addOrUpdateLocation(personaModel.getIdPersona(), model.getDireccion(), fecha, true);
        }

        return Util.objectToObject(PacienteDto.class, personaModel);
    }

    private boolean updatePatient(Integer idPersona, UsuarioPerfilDto model, Date date) {
        PacienteModel pacienteModel = pacienteRepository
                .findById(idPersona)
                .orElseThrow(() -> new ServiceException("Paciente no registrado"));
        boolean hasInscription = pacienteModel.getIndPadomi() == null ? false : pacienteModel.getIndPadomi();
        pacienteModel.setIndPadomi(model.getIndPadomi());
        pacienteModel.setDateModify(date);
        pacienteRepository.save(pacienteModel);
        return hasInscription;
    }

    private void sendInscripcionPadomiEmail(String email) {
        //String text = emailContentBuilder.padomiInscription();
        //emailSender.send(email, "Atención Domiciliaria PADOMI", text);
    }

    private void addOrUpdateLocation(Integer idPersona, DireccionDto model,
                                     Date date, boolean ignoreTipoVia) {
        if (model != null) {
            DireccionModel direccionModel = direccionRepository.findTopByIdPersona(idPersona);
            boolean isNew = direccionModel == null;
            if (isNew) {
                direccionModel = new DireccionModel();
                direccionModel.setDateCreate(date);
                direccionModel.setIdEstado(EstadoRegistro.REGISTRADO);
            } else {
                direccionModel.setDateModify(date);
            }
            direccionModel.setIdPersona(idPersona);
            direccionModel.setDescripcion(model.getDescripcion());
            direccionModel.setReferencia(model.getReferencia());
            direccionModel.setIdUbigeo(model.getUbigeo());
            if (!ignoreTipoVia) {
                direccionModel.setIdTipoVia(model.getIdTipoVia());
            }
            direccionModel.setNroLatitud(model.getNroLatitud());
            direccionModel.setNroLongitud(model.getNroLongitud());
            direccionRepository.save(direccionModel);
        }
    }

    private void addOrUpdateContact(Integer idPersona, ContactoDto model, Date date, boolean ignoreOperador) {
        if (model != null) {
            ContactoModel contactoModel = contactoRepository
                    .findByIdPersona(idPersona)
                    .orElse(null);
            if (contactoModel == null) {
                contactoModel = new ContactoModel();
                contactoModel.setDateCreate(date);
                contactoModel.setEstado(EstadoRegistro.REGISTRADO);
            } else {
                contactoModel.setDateModify(date);
            }
            contactoModel.setNroCelular(model.getNroCelular());
            contactoModel.setNroTelefonoFijo(model.getNroTelefonoFijo());
            if (!ignoreOperador) {
                contactoModel.setOperador(model.getOperador());
            }
            contactoModel.setEmail(model.getEmail());
            contactoModel.setIdPersona(idPersona);
            contactoRepository.save(contactoModel);
        }
    }

    public Boolean existVigente(String username) {
        //solamente se usa en el registro para validar si el usuario existe
        UsuarioModel user = usuarioRepository.findFirstByUsername(username);
        boolean isUserRegistered = user != null;
        return (isUserRegistered && (user.getEstado() == EstadoUsuario.ACTIVO));
    }

    @Override
    public UsuarioPerfilDto getPerfil(String tipDoc, String numDoc) {
        RequestGenericDto request = new RequestGenericDto();
        request.setTipDoc(tipDoc);
        request.setNumDoc(numDoc);
        return usuarioMyRepository.getPerfil(request);
    }

    private void registerRole(Integer idUsuario, boolean isUserRegistered) {
        boolean registerRole = true;
        if (isUserRegistered)
            registerRole = !usuarioRolRepository.existsByIdUsuarioAndIdRol(idUsuario, Role.USER);

        if (registerRole) {
            UsuarioRolModel usuarioRolModel = new UsuarioRolModel();
            usuarioRolModel.setIdRol(Role.USER);
            usuarioRolModel.setIdUsuario(idUsuario);
            usuarioRolRepository.save(usuarioRolModel);
        }
    }

    private PacienteModel patientRegister(Integer idPaciente, UsuarioRegisterDto model, Date currentDate) {
        PacienteModel pacienteModel = new PacienteModel();
        pacienteModel.setIdPaciente(idPaciente);
        pacienteModel.setCodCentro(model.getCodCentro());
        pacienteModel.setDateCreate(currentDate);
        pacienteModel.setIndPadomi(false);
        return pacienteRepository.save(pacienteModel);
    }

    private UsuarioModel userRegister(Integer idUsuario, String userName, String password, Date currentDate) {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setDateCreate(currentDate);
        usuarioModel.setEstado(EstadoUsuario.ACTIVO);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(6);
        usuarioModel.setPassword(passwordEncoder.encode(password));
        usuarioModel.setUsername(userName);
        usuarioModel.setIdUsuario(idUsuario);
        return usuarioRepository.save(usuarioModel);
    }

    private Integer registerOrUpdatePersona(UsuarioRegisterDto model, Date currentDate) {
        PersonaModel personaModel = personaRepository.getByTipoDocIdentAndNumeroDocIdentAndTipo(
                model.getTipoDocIden(),
                model.getNumeroDocIden(),
                TipoPersona.USUARIO);

        boolean isRegistered = personaModel != null;
        if (isRegistered) {
            personaModel.setDateModify(currentDate);
        } else {
            personaModel = new PersonaModel();
            personaModel.setTipoDocIdent(model.getTipoDocIden());
            personaModel.setNumeroDocIdent(model.getNumeroDocIden());
            personaModel.setDateCreate(currentDate);
        }
        String fechaNacimiento = model.getFecNacimiento();
        if (StringUtils.hasText(fechaNacimiento))
            personaModel.setFechaNacimiento(model.getFecNacimiento());

        personaModel.setPrimerNombre(model.getPrimerNombre());
        personaModel.setSegundoNombre(model.getSegundoNombre());
        personaModel.setApellidoPaterno(model.getApelidoPaterno());
        personaModel.setApellidoMaterno(model.getApellidoMaterno());
        personaModel.setTipo(TipoPersona.USUARIO);
        return personaRepository.save(personaModel).getIdPersona();
    }

    private void saveContactoFamiliar(Integer idPersona, UsuarioRegisterDto model, Date currentDate) {
        ContactoModel contactoModel = contactoRepository.findTopByIdPersona(idPersona);
        if (contactoModel == null) {
            contactoModel = new ContactoModel();
            contactoModel.setDateCreate(currentDate);
        } else {
            contactoModel.setDateModify(currentDate);
        }
        contactoModel.setIdPersona(idPersona);
        contactoModel.setNroTelefonoFijo(model.getNumTelefono());
        contactoModel.setNroCelular(model.getNumCelular());
        contactoModel.setEmail(model.getCorreo());
        contactoModel.setEstado(EstadoRegistro.REGISTRADO);
        contactoRepository.save(contactoModel);
    }

    private Boolean existContactoFamiliar(Integer idPersona) {
        PacienteFamiliarModel pacienteFamiliarModel = pacienteFamiliarRepository.
                findTopByIdPacienteAndEstado(idPersona, EstadoRegistro.REGISTRADO);
        return pacienteFamiliarModel != null;
    }

}
