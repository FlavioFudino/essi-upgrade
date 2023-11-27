package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.FamiliarPacienteRequestDto;
import gob.pe.essalud.trx.dto.ResponseDto;
import gob.pe.essalud.trx.service.FamiliarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(FamiliarController.FAMILIAR)
public class FamiliarController extends BaseController {
    public static final String FAMILIAR = "familiar";

    private final FamiliarService familiarService;

    @Autowired
    public FamiliarController(FamiliarService familiarService) {
        this.familiarService = familiarService;
    }


    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody FamiliarPacienteRequestDto model) {
        ResponseDto<FamiliarPacienteRequestDto> response = new ResponseDto<>();
        FamiliarPacienteRequestDto famRegistered = familiarService.save(model);
        response.setData(famRegistered);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> get(@RequestParam String tipoDocumento,
                                 @RequestParam String numeroDocumento) {
        ResponseDto<FamiliarPacienteRequestDto> response = new ResponseDto<>();
        FamiliarPacienteRequestDto famRegistered = familiarService.get(tipoDocumento, numeroDocumento);
        response.setData(famRegistered);
        return ResponseEntity.ok(response);
    }

}
