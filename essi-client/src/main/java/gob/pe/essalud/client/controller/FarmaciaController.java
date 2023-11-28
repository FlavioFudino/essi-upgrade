package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.base.BaseController;
import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.common.dto.ResponseSalogFarmaciaDto;
import gob.pe.essalud.client.common.http.ResponseType;
import gob.pe.essalud.client.dto.PacienteDto;
import gob.pe.essalud.client.dto.farmacia.PacienteRequestDto;
import gob.pe.essalud.client.service.FarmaciaService;
import gob.pe.essalud.client.service.ServiceException;
import gob.pe.essalud.client.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("")
public class FarmaciaController extends BaseController {
    private final FarmaciaService farmaciaService;
    private final SessionService session;

    @Autowired
    public FarmaciaController(FarmaciaService farmaciaService,
                              SessionService session) {
        this.farmaciaService = farmaciaService;
        this.session = session;
    }

    @PostMapping(value = "getListFarmacias")
    public ResponseEntity<ResponseDto> getListFarmacias(@Valid @RequestBody Map paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "getListFarmacias");
        ResponseDto<Map> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            Map resultado = farmaciaService.getListFarmacias(paramInput);
            response.setData(resultado);
        } catch (HttpStatusCodeException e) {
            String responseErrorServer = e.getResponseBodyAsString();
            response.setMessage(responseErrorServer);
            response.setCodResult(ResponseType.VALIDATION);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(value = "getTrackingRecetas")
    public ResponseEntity<ResponseSalogFarmaciaDto> getTrackingRecetas(@Valid @RequestBody Map paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "getTrackingRecetas");

        paramInput.put("TIPO_DOC", session.getTipoDocumento());
        paramInput.put("NRO_DOC", session.getNumeroDocumento());

        ResponseSalogFarmaciaDto<Map> response = new ResponseSalogFarmaciaDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            response = farmaciaService.getTrackingRecetas(paramInput);
            //response.setResult(resultado);
        } catch (HttpStatusCodeException e) {
            String responseErrorServer = e.getResponseBodyAsString();
            response.setMessage(responseErrorServer);
            response.setCode(ResponseType.VALIDATION);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(value = "saveFarmaciaPaciente")
    public ResponseEntity<ResponseDto> saveFarmaciaPaciente(@Valid @RequestBody PacienteRequestDto paciente) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "saveFarmaciaPaciente");
        ResponseDto<PacienteRequestDto> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            paciente.getPaciente().setTipoDocIdent(session.getTipoDocumento());
            paciente.getPaciente().setNumeroDocIdent(session.getNumeroDocumento());
            PacienteRequestDto data = farmaciaService.saveFarmaciaPaciente(paciente);
            response.setData(data);
        } catch (HttpStatusCodeException e) {
            String responseErrorServer = e.getResponseBodyAsString();
            response.setMessage(responseErrorServer);
            response.setCodResult(ResponseType.VALIDATION);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(value = "getFarmaciaPaciente")
    public PacienteRequestDto getFarmaciaPaciente(@Valid @RequestBody PacienteDto paramInput) {
        this.loggerDebug(Constantes.LOG_LEVEL_INFO, "->getFarmaciaPaciente");
        paramInput.setTipoDocIdent(session.getTipoDocumento());
        paramInput.setNumeroDocIdent(session.getNumeroDocumento());
        return farmaciaService.getFarmaciaPaciente(paramInput);
    }

}
