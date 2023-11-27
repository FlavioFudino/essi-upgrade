package gob.pe.essalud.client.client.medico;

import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.dto.medico.ListaSolicitudExamenRequestDto;
import gob.pe.essalud.client.dto.medico.ListaSolicitudExamenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "essiMedicoService", url = "${" + Constantes.URL_ENDPOINT_MEDICO_ESSI + "}")
public interface EssiMedico {

    @PostMapping(Constantes.URL_EXAMENES_AUXILIARES)
    //@Cacheable(value = "listaSolicitudExamen", key = "#paramInput.numDoc + '-' + #paramInput.tipDoc + '-' + #paramInput.codCen")
    ListaSolicitudExamenResponseDto listaSolicitudExamen(@RequestBody ListaSolicitudExamenRequestDto paramInput);

}
