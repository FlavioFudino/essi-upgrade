package gob.pe.essalud.client.client.reniec;

import gob.pe.essalud.client.common.constants.Constantes;
import gob.pe.essalud.client.dto.reniec.PersonaWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "reniecservice", url = "${" + Constantes.URL_ENDPOINT_RENIEC + "}")
public interface ReniecClient {

    @GetMapping(Constantes.URL_CONSULTA_DNI + "{numDocumento}")
    PersonaWrapper getPerson(@PathVariable String numDocumento);
}
