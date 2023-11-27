package gob.pe.essalud.trx.client;

import gob.pe.essalud.trx.common.constants.Constantes;
import gob.pe.essalud.trx.dto.emailservice.RecuperarClaveMobileRequestDto;
import gob.pe.essalud.trx.dto.emailservice.RecuperarClaveWebRequestDto;
import gob.pe.essalud.trx.dto.emailservice.RegistrarUsuarioRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "emailservice", url = "${" + Constantes.URL_ENDPOINT_BASE_EMAIL_SERVICE + "}")
public interface EmailServiceClient {

    @PostMapping(Constantes.REGISTRAR_USUARIO)
    boolean registrarUsuario(@RequestBody RegistrarUsuarioRequestDto requestDto);

    @PostMapping(Constantes.RECUPERAR_CLAVE_WEB)
    boolean recuperarClaveWeb(@RequestBody RecuperarClaveWebRequestDto requestDto);

    @PostMapping(Constantes.RECUPERAR_CLAVE_MOBILE)
    boolean recuperarClaveMobile(@RequestBody RecuperarClaveMobileRequestDto requestDto);

}
