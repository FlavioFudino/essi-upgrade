package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.ClaveChangeRequestDto;
import gob.pe.essalud.trx.dto.ClaveRecoveryRequestDto;
import gob.pe.essalud.trx.dto.ClaveRecoveryResponseDto;
import gob.pe.essalud.trx.dto.ResponseDto;
import gob.pe.essalud.trx.service.ClaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(ClaveController.CLAVE)
public class ClaveController extends BaseController {
    public static final String CLAVE = "clave";
    private static final String RECOVERY = "recovery";
    private final ClaveService claveService;

    @Autowired
    public ClaveController(ClaveService claveService) {
        this.claveService = claveService;
    }

    @PostMapping(RECOVERY)
    public ResponseEntity<?> recovery(@Valid @RequestBody ClaveRecoveryRequestDto claveRecoveryRequestDto) {
        ResponseDto<ClaveRecoveryResponseDto> response = new ResponseDto<>();
        ClaveRecoveryResponseDto claveRecovery = claveService.recovery(claveRecoveryRequestDto);
        response.setData(claveRecovery);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ClaveChangeRequestDto cambioClaveRequestDto) {
        ResponseDto<ClaveRecoveryResponseDto> response = new ResponseDto<>();
        ClaveRecoveryResponseDto claveRecovery = claveService.save(cambioClaveRequestDto);
        response.setData(claveRecovery);
        return ResponseEntity.ok(response);
    }

}
