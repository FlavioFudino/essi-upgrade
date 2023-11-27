package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.*;
import gob.pe.essalud.trx.dto.paciente.RiesgoDiabetesEvaluarRequestDto;
import gob.pe.essalud.trx.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("paciente")
public class PacienteController extends BaseController {
    private final PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping(value = "saveFarmaciaPaciente", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveFarmaciaPaciente(@Valid @RequestBody(required = true) PacienteRequestDto paciente) {
        ResponseDto<PacienteRequestDto> response = new ResponseDto<>();
        pacienteService.save(paciente);
        response.setData(paciente);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "getDireccionFarmacia", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPacienteByDocumento(@Valid @RequestBody(required = true) PacienteDto paciente) {
        ResponseDto<PacienteRequestDto> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            PacienteRequestDto requestDto = pacienteService.
                    getDireccionFarmacia(paciente.getTipoDocIdent(), paciente.getNumeroDocIdent());
            response.setData(requestDto);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(value = "getPacienteByNumero", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPacienteByNumero(@Valid @RequestBody(required = true) PacienteDto paciente) {
        ResponseDto<PacienteDto> response = new ResponseDto<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            PacienteDto requestDto = pacienteService.
                    getPacienteByNumero(paciente.getNumeroDocIdent());
            response.setData(requestDto);
        } catch (Exception e) {
            this.loggerException(e.getMessage(), e);
        }
        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping("getPacienteAsegurado")
    public ResponseEntity<?> getPacienteAsegurado(@Valid @RequestBody(required = true) PacienteAseguradoRequestDto input) {
        return ResponseEntity.ok(pacienteService.getPacienteAsegurado(input));
    }

    @PostMapping("updateCentroPaciente")
    public ResponseEntity<?> updateCentroPaciente(@RequestBody UpdateCentroPacienteRequestDto input) {
        return ResponseEntity.ok(pacienteService.updateCentroPaciente(input));
    }

    @PostMapping("getRiesgoDiabetes")
    public ResponseEntity<?> getRiesgoDiabetes(@RequestBody RequestGenericDto input) {
        return ResponseEntity.ok(pacienteService.getRiesgoDiabetes(input));
    }

    @PostMapping("riesgoDiabetesEvaluar")
    public ResponseEntity<?> riesgoDiabetesEvaluar(@RequestBody RiesgoDiabetesEvaluarRequestDto input) {
        return ResponseEntity.ok(pacienteService.riesgoDiabetesEvaluar(input));
    }

}
