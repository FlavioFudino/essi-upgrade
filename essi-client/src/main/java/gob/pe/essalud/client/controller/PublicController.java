package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.client.trx.TrxClient;
import gob.pe.essalud.client.dto.bi.ConsultaCentroLatLongDto;
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

    private final TrxClient trxClient;

    public PublicController(TrxClient trxClient) {
        this.trxClient = trxClient;
    }

    @GetMapping(CONSULTA_CENTRO_LAT_LONG + "/{cenAsiCod}")
    public ConsultaCentroLatLongDto consultaCentroLatLong(@PathVariable String cenAsiCod) {
        return trxClient.consultaCentroLatLong(cenAsiCod);
    }

    @GetMapping("lista-ipress-cita")
    public ResponseEntity<?> getListaIpressCita() {
        return ResponseEntity.ok(trxClient.getListaIpressCita());
    }

    @GetMapping("lista-servicios-ipress/{idIpress}")
    public ResponseEntity<?> getListaServiciosIpress(@PathVariable int idIpress) {
        return ResponseEntity.ok(trxClient.getListaServiciosIpress(idIpress));
    }

    @GetMapping("lista-redes")
    public ResponseEntity<?> getListaRedes() {
        return ResponseEntity.ok(trxClient.getListaRedes());
    }

    @GetMapping("lista-ipress-sin-cita/{red}")
    public ResponseEntity<?> getListaIpressSinCita(@PathVariable String red) {
        return ResponseEntity.ok(trxClient.getListaIpressSinCita(red));
    }

}
