package gob.pe.essalud.emailservice.service;

import gob.pe.essalud.emailservice.dto.common.ActivarCuentaRequestDto;
import gob.pe.essalud.emailservice.dto.common.RecuperarClaveWebRequestDto;

public interface SomosEssaludService {

    boolean recuperarClave(RecuperarClaveWebRequestDto input);

    boolean activarCuenta(ActivarCuentaRequestDto input);

}
