package gob.pe.essalud.trx.controller;

import gob.pe.essalud.trx.dto.bi.ConsultaCentroLatLongDto;
import gob.pe.essalud.trx.service.PublicService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PublicController.PUBLIC)
public class PublicController {

    public static final String PUBLIC = "p";

    private static final String CONSULTA_CENTRO_LAT_LONG = "ccll";

    private final PublicService publicService;

    public PublicController(PublicService publicService) {
        this.publicService = publicService;
    }

    @GetMapping(CONSULTA_CENTRO_LAT_LONG + "/{cenAsiCod}")
    public ConsultaCentroLatLongDto consultaCentroLatLong(@PathVariable String cenAsiCod) {
        return publicService.consultaCentroLatLong(cenAsiCod);
    }

    @GetMapping("lista-ipress-cita")
    public ResponseEntity<?> getListaIpressCita() {
        return ResponseEntity.ok(publicService.getListaIpressCita());
    }

    @GetMapping("lista-servicios-ipress/{idIpress}")
    public ResponseEntity<?> getListaServiciosIpress(@PathVariable int idIpress) {
        return ResponseEntity.ok(publicService.getListaServiciosIpress(idIpress));
    }

    @GetMapping("lista-redes")
    public ResponseEntity<?> getListaRedes() {
        return ResponseEntity.ok(publicService.getListaRedes());
    }

    @GetMapping("lista-ipress-sin-cita/{red}")
    public ResponseEntity<?> getListaIpressSinCita(@PathVariable String red) {
        return ResponseEntity.ok(publicService.getListaIpressSinCita(red));
    }

}
