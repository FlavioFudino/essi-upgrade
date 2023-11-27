package gob.pe.essalud.client.controller;

import gob.pe.essalud.client.client.CamServiceClient;
import gob.pe.essalud.client.dto.cam.ApiCamResponse;
import gob.pe.essalud.client.dto.cam.CamRegistrarSolicitudRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cam")
@RequiredArgsConstructor
public class CamController {

    private final CamServiceClient camServiceClient;

    @GetMapping("cam/unidad-operativa")
    ApiCamResponse<?> getUnidadOperativaList(@RequestParam("codCentro") String codCentro) {
        return camServiceClient.getUnidadOperativaList(codCentro);
    }

    @GetMapping("afiliado/estado-solicitud-taller")
    ApiCamResponse<?> getEstadoSolicitud(@RequestParam("tipDoc") String tipDoc, @RequestParam("numDoc") String numDoc) {
        return camServiceClient.getEstadoSolicitud(tipDoc, numDoc);
    }

    @PostMapping("afiliado/register-solicitud")
    ApiCamResponse<?> registrarSolicitud(@RequestBody CamRegistrarSolicitudRequestDto input) {
        return camServiceClient.registrarSolicitud(input);
    }

}
