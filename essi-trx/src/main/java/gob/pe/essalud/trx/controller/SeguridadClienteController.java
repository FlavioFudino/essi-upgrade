package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.base.BaseController;
import gob.pe.essalud.trx.dto.SeguridadClienteDetalleRequestDto;
import gob.pe.essalud.trx.dto.SeguridadClienteDetalleResponseDto;
import gob.pe.essalud.trx.dto.SeguridadClienteDto;
import gob.pe.essalud.trx.dto.SeguridadClienteRequestDto;
import gob.pe.essalud.trx.service.SeguridadClienteDetalleService;
import gob.pe.essalud.trx.service.SeguridadClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(SeguridadClienteController.SEGURIDAD_CLIENTE)
public class SeguridadClienteController extends BaseController {

    public static final String SEGURIDAD_CLIENTE = "seguridad-cliente";

    private final String GUARDAR_DETALLE = "guardar-detalle";

    private final SeguridadClienteService seguridadClienteService;
    private final SeguridadClienteDetalleService seguridadClienteDetalleService;

    @Autowired
    public SeguridadClienteController(
            SeguridadClienteService seguridadClienteService,
            SeguridadClienteDetalleService seguridadClienteDetalleService) {

        this.seguridadClienteService = seguridadClienteService;
        this.seguridadClienteDetalleService = seguridadClienteDetalleService;
    }

    @GetMapping
    public ResponseEntity<SeguridadClienteDto> get(@RequestParam String ipCliente) {
        SeguridadClienteDto response = seguridadClienteService.findByIpCliente(ipCliente);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<SeguridadClienteDto> save(@Valid @RequestBody SeguridadClienteRequestDto requestDto) {
        SeguridadClienteDto response = seguridadClienteService.save(requestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping(GUARDAR_DETALLE)
    public ResponseEntity<SeguridadClienteDetalleResponseDto> saveDetalle(@Valid @RequestBody SeguridadClienteDetalleRequestDto requestDto) {
        SeguridadClienteDetalleResponseDto response = seguridadClienteDetalleService.save(requestDto);
        return ResponseEntity.ok(response);
    }

}
