package gob.pe.essalud.trx.service.impl;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.common.constants.EstadoRegistro;
import gob.pe.essalud.trx.common.constants.TipoPersona;
import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.ContactoDto;
import gob.pe.essalud.trx.dto.DireccionDto;
import gob.pe.essalud.trx.dto.FamiliarPacienteRequestDto;
import gob.pe.essalud.trx.dto.FamiliarRequestDto;
import gob.pe.essalud.trx.exception.ServiceException;
import gob.pe.essalud.trx.jpa.model.*;
import gob.pe.essalud.trx.jpa.repository.*;
import gob.pe.essalud.trx.repository.FamiliarMyRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.service.FamiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FamiliarServiceImpl extends BaseService implements FamiliarService {
    private final PersonaRepository personaRepository;
    private final FamiliarRepository familiarRepository;
    private final PacienteFamiliarRepository pacienteFamiliarRepository;
    private final ParametroRepository parametroRepository;
    private final DireccionRepository direccionRepository;
    private final ContactoRepository contactoRepository;
    private final FamiliarMyRepository familiarMyRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public FamiliarServiceImpl(PersonaRepository personaRepository, FamiliarRepository familiarRepository, PacienteFamiliarRepository pacienteFamiliarRepository, ParametroRepository parametroRepository, DireccionRepository direccionRepository, ContactoRepository contactoRepository, FamiliarMyRepository familiarMyRepository) {
        this.personaRepository = personaRepository;
        this.familiarRepository = familiarRepository;
        this.pacienteFamiliarRepository = pacienteFamiliarRepository;
        this.parametroRepository = parametroRepository;
        this.direccionRepository = direccionRepository;
        this.contactoRepository = contactoRepository;
        this.familiarMyRepository = familiarMyRepository;
    }

    @Transactional
    public FamiliarPacienteRequestDto save(FamiliarPacienteRequestDto familiarRequestDto) {
        this.loggerInfo("Inicio save", dateFormat.format(new Date()));
        Date fechaServer = parametroRepository.getFecha();

        //Persona (validar que exista el usuario que esta registrando a su familiar)
        PersonaModel pacienteModel = personaRepository.getByTipoDocIdentAndNumeroDocIdentAndTipo(
                familiarRequestDto.getTipoDocIdent(),
                familiarRequestDto.getNumeroDocIdent(),
                TipoPersona.USUARIO);

        boolean isPacienteRegistered = pacienteModel != null;
        if (!isPacienteRegistered)
            throw new ServiceException("El paciente no se encuentra registrado");

        familiarRequestDto.setIdPaciente(pacienteModel.getIdPersona());
        if (saveFamiliar(familiarRequestDto, fechaServer)) {
            saveContactoFamiliar(familiarRequestDto, fechaServer);
            saveDirecionFamiliar(familiarRequestDto, fechaServer);
        }
        this.loggerInfo("Fin save", dateFormat.format(new Date()));
        return familiarRequestDto;
    }

    private Boolean saveFamiliar(FamiliarPacienteRequestDto familiarRequestDto, Date currentDate) {
        FamiliarRequestDto f = familiarRequestDto.getFamiliar();

        Integer idUsuario = familiarRequestDto.getIdPaciente();

        //Persona (validar que el familiar que esta agregando exista entre los que tiene relacionados)
        //si no esta, debera registrarlo como nuevo y relacionarlo

        PersonaModel personaModel = personaRepository.getByTipoDocIdentAndNumeroDocIdentAndTipoAndIdUsuarioRegistra(
                f.getTipoDocIdent(),
                f.getNumeroDocIdent(),
                TipoPersona.FAMILIAR,
                idUsuario);

        boolean isPersonaRegistered = personaModel != null;

        if (!isPersonaRegistered) {
            personaModel = Util.objectToObject(PersonaModel.class, f);
            personaModel.setDateCreate(currentDate);
        }
        else {
            personaModel.setApellidoPaterno(f.getApellidoPaterno());
            personaModel.setApellidoMaterno(f.getApellidoMaterno());
            personaModel.setPrimerNombre(f.getPrimerNombre());
            personaModel.setSegundoNombre(f.getSegundoNombre());
            personaModel.setFechaNacimiento(f.getFechaNacimiento());
            personaModel.setDateModify(currentDate);
        }
        personaModel.setIdUsuarioRegistra(idUsuario);
        personaModel.setTipo(TipoPersona.FAMILIAR);
        personaRepository.save(personaModel);

        FamiliarModel familiarModel = familiarRepository.findByIdFamiliar(personaModel.getIdPersona());
        boolean isFamiliarRegistered = familiarModel != null;
        if (!isFamiliarRegistered) {
            //Familiar
            familiarModel = new FamiliarModel();
            familiarModel.setIdFamiliar(personaModel.getIdPersona());
            familiarModel.setDateCreate(currentDate);
            familiarRepository.save(familiarModel);
        }
        familiarRequestDto.getFamiliar().setIdFamiliar(personaModel.getIdPersona());
        savePacienteFamiliar(familiarRequestDto, currentDate);
        return true;
    }


    private Boolean savePacienteFamiliar(FamiliarPacienteRequestDto familiarRequestDto, Date currentDate) {
        Boolean isFamiliarDeleted = false;
        FamiliarRequestDto f = familiarRequestDto.getFamiliar();

        PacienteFamiliarModel pacienteFamiliarModel = pacienteFamiliarRepository.
                findTopByIdPacienteAndEstado(familiarRequestDto.getIdPaciente(), EstadoRegistro.REGISTRADO);

        boolean isFamiliarRegistered = pacienteFamiliarModel != null;
        if ((isFamiliarRegistered) && (pacienteFamiliarModel.getIdFamiliar() != f.getIdFamiliar())) {
            pacienteFamiliarModel.setDateModify(currentDate);
            pacienteFamiliarModel.setEstado(EstadoRegistro.ANULADO);
            pacienteFamiliarRepository.save(pacienteFamiliarModel);
            isFamiliarDeleted = true;
        }
        if ((!isFamiliarRegistered) || (isFamiliarDeleted)) {
            pacienteFamiliarModel = new PacienteFamiliarModel();
            pacienteFamiliarModel.setDateCreate(currentDate);
        } else {
            pacienteFamiliarModel.setDateModify(currentDate);
        }
        pacienteFamiliarModel.setParentesco(f.getParentesco());
        pacienteFamiliarModel.setEstado(EstadoRegistro.REGISTRADO);
        pacienteFamiliarModel.setIdFamiliar(f.getIdFamiliar());
        pacienteFamiliarModel.setIdPaciente(familiarRequestDto.getIdPaciente());
        pacienteFamiliarRepository.save(pacienteFamiliarModel);
        return true;
    }


    private Boolean saveContactoFamiliar(FamiliarPacienteRequestDto familiarRequestDto, Date currentDate) {
        ContactoDto c = familiarRequestDto.getFamiliar().getContacto();
        ContactoModel contactoModel = contactoRepository.findTopByIdPersona(familiarRequestDto.getFamiliar().getIdFamiliar());
        boolean isContactoRegistered = contactoModel != null;
        ContactoModel contactoModifiedModel = Util.objectToObject(ContactoModel.class, c);
        if (!isContactoRegistered) {
            contactoModifiedModel.setDateCreate(currentDate);
        } else {
            contactoModifiedModel.setIdContacto(contactoModel.getIdContacto());
            contactoModifiedModel.setDateModify(currentDate);
        }
        contactoModifiedModel.setIdPersona(familiarRequestDto.getFamiliar().getIdFamiliar());
        contactoModifiedModel.setEstado(EstadoRegistro.REGISTRADO);
        contactoRepository.save(contactoModifiedModel);
        return true;
    }

    private Boolean saveDirecionFamiliar(FamiliarPacienteRequestDto familiarRequestDto, Date currentDate) {
        DireccionDto d = familiarRequestDto.getFamiliar().getDireccion();
        DireccionModel direccionModel = direccionRepository.findTopByIdPersona(familiarRequestDto.getFamiliar().getIdFamiliar());
        DireccionModel direccionModifiedModel = Util.objectToObject(DireccionModel.class, d);
        boolean isDireccionRegistered = direccionModel != null;
        if (!isDireccionRegistered) {
            direccionModifiedModel.setDateCreate(currentDate);
        } else {
            direccionModifiedModel.setIdDireccion(direccionModel.getIdDireccion());
            direccionModifiedModel.setDateModify(currentDate);
            direccionModifiedModel.setDateCreate(direccionModel.getDateCreate());
        }
        direccionModifiedModel.setIdUbigeo(d.getUbigeo());
        direccionModifiedModel.setIdPersona(familiarRequestDto.getFamiliar().getIdFamiliar());
        direccionModifiedModel.setIdEstado(EstadoRegistro.REGISTRADO);
        direccionRepository.save(direccionModifiedModel);
        return true;
    }

    @Override
    public FamiliarPacienteRequestDto get(String tipoDocumento, String numeroDocumento) {
        Map<String, String> params = new HashMap<>();
        params.put("tipoDocIden", tipoDocumento);
        params.put("numeroDocIden", numeroDocumento);
        return familiarMyRepository.getDireccionFamiliar(params);
    }

}
