package gob.pe.essalud.client.client;

import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.dto.citt.ConsultaCittRespDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "consultacitt", url = "${" + Constantes.URL_ENDPOINT_BASE_CONSULTA_CITT_SERVICE + "}")
public interface ConsultaCittClient {

    @GetMapping(Constantes.CONSULTA_CITT_BUSCAR)
    //@Cacheable(value = "buscarCitt", key = "#tipoDoc + '-' + #numDoc")
    ConsultaCittRespDto buscarCitt(@PathVariable String tipoDoc, @PathVariable String numDoc);

}
