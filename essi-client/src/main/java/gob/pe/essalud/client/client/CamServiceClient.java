package gob.pe.essalud.client.client;

import gob.pe.essalud.client.dto.cam.ApiCamResponse;
import gob.pe.essalud.client.dto.cam.CamRegistrarSolicitudRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "camservice", url = "${base.cam-service-url}")
public interface CamServiceClient {

    @GetMapping("cam/unidad-operativa")
    ApiCamResponse<?> getUnidadOperativaList(@RequestParam("codCentro") String codCentro);

    @GetMapping("afiliado/estado-solicitud-taller")
    ApiCamResponse<?> getEstadoSolicitud(@RequestParam("tipDoc") String tipDoc, @RequestParam("numDoc") String numDoc);

    @PostMapping("afiliado/register-solicitud")
    ApiCamResponse<?> registrarSolicitud(@RequestBody CamRegistrarSolicitudRequestDto input);

}
