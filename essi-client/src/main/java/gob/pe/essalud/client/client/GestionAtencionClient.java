package gob.pe.essalud.client.client;

import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.dto.gestion_atencion.GaRegistrarColaRequestDto;
import gob.pe.essalud.client.dto.gestion_atencion.GaRegistrarColaResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gestionatencionervice", url = "${base.gestion-atencion-url}")
public interface GestionAtencionClient {

    @PostMapping("miconsulta/registrar-cola")
    ResponseDto<GaRegistrarColaResponseDto> registrarCola(@RequestBody GaRegistrarColaRequestDto input);

}
