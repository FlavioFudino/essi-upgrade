package gob.pe.essalud.trx.service.impl;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.common.constants.EstadoRegistro;
import gob.pe.essalud.trx.common.constants.TipoPersona;
import gob.pe.essalud.trx.common.util.RiesgoDiabetesUtil;
import gob.pe.essalud.trx.common.util.Util;
import gob.pe.essalud.trx.dto.*;
import gob.pe.essalud.trx.dto.paciente.*;
import gob.pe.essalud.trx.exception.ServiceException;
import gob.pe.essalud.trx.jpa.model.*;
import gob.pe.essalud.trx.jpa.repository.*;
import gob.pe.essalud.trx.repository.PacienteMyRepository;
import gob.pe.essalud.trx.repository.ParametroRepository;
import gob.pe.essalud.trx.service.PacienteService;
import gob.pe.essalud.trx.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PacienteServiceImpl extends BaseService implements PacienteService {

    private final PersonaRepository personaRepository;
    private final ParametroRepository parametroRepository;
    private final DireccionRepository direccionRepository;
    private final FarmaciaRepository farmaciaRepository;
    private final PacienteFarmaciaRepository pacienteFarmaciaRepository;
    private final ContactoRepository contactoRepository;
    private final PacienteMyRepository pacienteMyRepository;
    private final PacienteRepository pacienteRepository;
    private final RiesgoDiabetesRepository riesgoDiabetesRepository;
    private final RiesgoDiabetesRespuestaRepository riesgoDiabetesRespuestaRepository;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Transactional
    public PacienteRequestDto save(PacienteRequestDto pacienteRequestDto) {
        this.loggerInfo("Inicio save paciente", dateFormat.format(new Date()));
        Date currentDate = parametroRepository.getFecha();

        Date fechaServer = parametroRepository.getFecha();
        PersonaModel personaModel = personaRepository.getByTipoDocIdentAndNumeroDocIdentAndTipo(
                pacienteRequestDto.getPaciente().getTipoDocIdent(),
                pacienteRequestDto.getPaciente().getNumeroDocIdent(),
                TipoPersona.USUARIO);

        boolean isPersonRegistered = personaModel != null;

        // Person Register
        if (!isPersonRegistered)
            throw new ServiceException("El paciente no se encuentra registrado");

        //contactos
        pacienteRequestDto.getPaciente().setIdPaciente(personaModel.getIdPersona());
        contactoRegister(pacienteRequestDto.getPaciente(), fechaServer);

        //Direccion Paciente
        DireccionDto d = pacienteRequestDto.getDireccionPaciente();
        DireccionModel direccionModel = direccionRepository.findTopByIdPersona(personaModel.getIdPersona());
        DireccionModel direccionModifiedModel = Util.objectToObject(DireccionModel.class, d);

        boolean isDireccionRegistered = direccionModel != null;
        if (!isDireccionRegistered) {
            direccionModifiedModel.setDateCreate(currentDate);
        } else {
            direccionModifiedModel.setIdDireccion(direccionModel.getIdDireccion());
            direccionModifiedModel.setDateModify(currentDate);
            direccionModifiedModel.setDateCreate(direccionModel.getDateCreate());
        }
        direccionModifiedModel.setIdPersona(personaModel.getIdPersona());
        direccionModifiedModel.setIdEstado(EstadoRegistro.REGISTRADO);
        direccionRepository.save(direccionModifiedModel);

        //Farmacia
        FarmaciaModel farmaciaModel = farmaciaRepository.findTopByIdFarmacia(pacienteRequestDto.getDireccionFarmacia().getIdFarmacia());
        if (farmaciaModel == null) {
            farmaciaModel = Util.objectToObject(FarmaciaModel.class, pacienteRequestDto.getDireccionFarmacia());
            farmaciaModel.setIdEstado(EstadoRegistro.REGISTRADO);
            farmaciaModel.setDateCreate(currentDate);
            farmaciaRepository.save(farmaciaModel);
        }
        //farmacia paciente
        PacienteFarmaciaModel pacienteFarmaciaModel = pacienteFarmaciaRepository.findTopByIdPaciente(personaModel.getIdPersona());
        if (pacienteFarmaciaModel == null) {
            pacienteFarmaciaModel = new PacienteFarmaciaModel();
            pacienteFarmaciaModel.setDateCreate(currentDate);
            pacienteFarmaciaModel.setEstado(EstadoRegistro.REGISTRADO);
            pacienteFarmaciaModel.setIdFarmacia(farmaciaModel.getIdFarmacia());
            pacienteFarmaciaModel.setIdPaciente(personaModel.getIdPersona());
            pacienteFarmaciaRepository.save(pacienteFarmaciaModel);
        } else {
            if (!pacienteFarmaciaModel.getIdFarmacia().equals(farmaciaModel.getIdFarmacia())) {
                //Anulo Anterior
                pacienteFarmaciaModel.setDateModify(currentDate);
                pacienteFarmaciaModel.setEstado(EstadoRegistro.ANULADO);
                pacienteFarmaciaRepository.save(pacienteFarmaciaModel);
                //Nuevo
                pacienteFarmaciaModel = new PacienteFarmaciaModel();
                pacienteFarmaciaModel.setDateCreate(currentDate);
                pacienteFarmaciaModel.setEstado(EstadoRegistro.REGISTRADO);
                pacienteFarmaciaModel.setIdFarmacia(farmaciaModel.getIdFarmacia());
                pacienteFarmaciaModel.setIdPaciente(personaModel.getIdPersona());
                pacienteFarmaciaRepository.save(pacienteFarmaciaModel);
            }
            pacienteFarmaciaModel.setDateModify(currentDate);
            pacienteFarmaciaModel.setEstado(EstadoRegistro.REGISTRADO);
            pacienteFarmaciaModel.setIdFarmacia(farmaciaModel.getIdFarmacia());
            pacienteFarmaciaRepository.save(pacienteFarmaciaModel);
        }
        this.loggerInfo("Fin save paciente", dateFormat.format(new Date()));
        return pacienteRequestDto;
    }

    @Override
    public PacienteRequestDto getDireccionFarmacia(String tipoDocumento, String numeroDocumento) {
        Map<String, String> params = new HashMap<>();
        params.put("tipoDocIden", tipoDocumento);
        params.put("numeroDocIden", numeroDocumento);
        return pacienteMyRepository.getDireccionFarmacia(params);
    }

    @Override
    public PacienteDto getPacienteByNumero(String numeroDocumento) {
        Map<String, String> params = new HashMap<>();
        params.put("numeroDocIden", numeroDocumento);
        return pacienteMyRepository.getPacienteByNumero(params);
    }

    @Override
    public PacienteAseguradoDto getPacienteAsegurado(PacienteAseguradoRequestDto input) {
        Map<String, String> params = new HashMap<>();
        params.put("tipoDoc", input.getTipoDoc());
        params.put("numDoc", input.getNumDoc());
        params.put("fecNac", input.getFecNac());
        return pacienteMyRepository.getPacienteAsegurado(params);
    }

    @Override
    public boolean updateCentroPaciente(UpdateCentroPacienteRequestDto input) {
        Optional<PacienteModel> pacienteModel = pacienteRepository.findFirstByIdPaciente(input.getIdPaciente().intValue());
        if (pacienteModel.isPresent()) {
            pacienteModel.get().setCodCentro(input.getCodCentro().trim());
            pacienteRepository.save(pacienteModel.get());
            return true;
        }

        return false;
    }

    private ContactoModel contactoRegister(PacienteDto model, Date currentDate) {
        ContactoModel contactoModel = contactoRepository.findTopByIdPersona(model.getIdPaciente());
        if (contactoModel == null)
            contactoModel = new ContactoModel();
        contactoModel.setEmail(model.getEmail());
        contactoModel.setDateCreate(currentDate);
        contactoModel.setIdPersona(model.getIdPaciente());
        contactoModel.setNroCelular(model.getNroCelular());
        contactoModel.setOperador(model.getOperador());
        contactoModel.setNroTelefonoFijo(model.getNroTelefonoFijo());
        contactoModel.setEstado(EstadoRegistro.REGISTRADO);
        return contactoRepository.save(contactoModel);
    }

    @Override
    public GetRiesgoDiabetesResponseDto getRiesgoDiabetes(RequestGenericDto input) {
        PersonaModel personaModel = personaRepository.getByTipoDocIdentAndNumeroDocIdentAndTipo(
                input.getTipDoc(),
                input.getNumDoc(),
                TipoPersona.USUARIO);

        if (personaModel == null) {
            throw new ServiceException("El paciente no se encuentra registrado");
        }

        GetRiesgoDiabetesResponseDto responseDto = null;

        GetRiesgoDiabetesDto riesgoDiabetesDto = pacienteMyRepository.getRiesgoDiabetes(personaModel.getIdPersona());
        if (riesgoDiabetesDto != null) {
            responseDto = new GetRiesgoDiabetesResponseDto();
            responseDto.setTipoRiesgo(riesgoDiabetesDto.getTipoRiesgo());
            responseDto.setFecha(riesgoDiabetesDto.getFecha());

            List<GetRiesgoDiabetesRespuestaDto> rptas = pacienteMyRepository
                    .getRiesgoDiabetesRespuestas(riesgoDiabetesDto.getIdRiesgoDiabetes());

            responseDto.setRespuestas(rptas);
        }

        return responseDto;
    }

    @SneakyThrows
    @Transactional
    @Override
    public RiesgoDiabetesEvaluarResponseDto riesgoDiabetesEvaluar(RiesgoDiabetesEvaluarRequestDto input) {
        PersonaModel personaModel = personaRepository.getByTipoDocIdentAndNumeroDocIdentAndTipo(
                input.getTipDoc(),
                input.getNumDoc(),
                TipoPersona.USUARIO);

        if (personaModel == null) {
            throw new ServiceException("El paciente no se encuentra registrado");
        }

        Date currentDate = DateUtil.currentDateLima();

        RiesgoDiabetesModel riesgoDiabetesModel = new RiesgoDiabetesModel();
        riesgoDiabetesModel.setIdPersona(personaModel.getIdPersona());
        riesgoDiabetesModel.setTipoRiesgo(0);
        riesgoDiabetesModel.setPuntos(0);
        riesgoDiabetesModel.setFechaRegistro(currentDate);
        riesgoDiabetesRepository.save(riesgoDiabetesModel);

        int puntosTotales = 0;
        int preguntaNum = 1;
        RiesgoDiabetesRespuestaModel diabetesRespuestaModel = null;

        //1. Pregunta "Edad"
        int edad = DateUtil.calculateAge(personaModel.getFechaNacimiento());
        int puntosRpta = RiesgoDiabetesUtil.getPuntosPregunta(preguntaNum, edad, input.getGenero());
        puntosTotales += puntosRpta;

        diabetesRespuestaModel = new RiesgoDiabetesRespuestaModel();
        diabetesRespuestaModel.setIdRiesgoDiabetes(riesgoDiabetesModel.getIdRiesgoDiabetes());
        diabetesRespuestaModel.setPregunta(preguntaNum);
        diabetesRespuestaModel.setTipo(RiesgoDiabetesUtil.getTipoRespuesta(edad));
        diabetesRespuestaModel.setValor(String.valueOf(edad));
        diabetesRespuestaModel.setPuntos(puntosRpta);
        riesgoDiabetesRespuestaRepository.save(diabetesRespuestaModel);

        //2. Pregunta "IMC"
        int peso = Integer.parseInt(input.getRespuestas().get(0));
        int talla = Integer.parseInt(input.getRespuestas().get(1));
        double imc = RiesgoDiabetesUtil.calcularIMC(peso, talla);
        preguntaNum++;

        puntosRpta = RiesgoDiabetesUtil.getPuntosPregunta(preguntaNum, edad, input.getGenero());
        puntosTotales += puntosRpta;

        diabetesRespuestaModel = new RiesgoDiabetesRespuestaModel();
        diabetesRespuestaModel.setIdRiesgoDiabetes(riesgoDiabetesModel.getIdRiesgoDiabetes());
        diabetesRespuestaModel.setPregunta(preguntaNum);
        diabetesRespuestaModel.setTipo(1);
        diabetesRespuestaModel.setValor(String.valueOf(imc));
        diabetesRespuestaModel.setRef(String.format("%s|%s", peso, talla));
        diabetesRespuestaModel.setPuntos(puntosRpta);
        riesgoDiabetesRespuestaRepository.save(diabetesRespuestaModel);

        for (int i = 2; i < input.getRespuestas().size(); i++) {
            var sValue =  input.getRespuestas().get(i);
            int nValue = Integer.parseInt(sValue);

            puntosRpta = RiesgoDiabetesUtil.getPuntosPregunta(++preguntaNum, nValue, input.getGenero());
            puntosTotales += puntosRpta;

            diabetesRespuestaModel = new RiesgoDiabetesRespuestaModel();
            diabetesRespuestaModel.setIdRiesgoDiabetes(riesgoDiabetesModel.getIdRiesgoDiabetes());
            diabetesRespuestaModel.setPregunta(preguntaNum);
            diabetesRespuestaModel.setTipo(RiesgoDiabetesUtil.getTipoRespuesta(nValue));
            diabetesRespuestaModel.setValor(sValue);
            diabetesRespuestaModel.setPuntos(puntosRpta);
            riesgoDiabetesRespuestaRepository.save(diabetesRespuestaModel);
        }

        riesgoDiabetesModel.setTipoRiesgo(RiesgoDiabetesUtil.getTipoRiesgo(puntosTotales));
        riesgoDiabetesModel.setPuntos(puntosTotales);
        riesgoDiabetesRepository.save(riesgoDiabetesModel);

        var responseDto = new RiesgoDiabetesEvaluarResponseDto();
        responseDto.setTipoRiesgo(riesgoDiabetesModel.getTipoRiesgo());
        responseDto.setFecha(DateUtil.format(currentDate, "dd/MM/yyyy hh:mm a"));
        return responseDto;
    }

}
