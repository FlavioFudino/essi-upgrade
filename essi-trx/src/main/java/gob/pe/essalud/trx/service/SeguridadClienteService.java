package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.SeguridadClienteDto;
import gob.pe.essalud.trx.dto.SeguridadClienteRequestDto;

public interface SeguridadClienteService {
    SeguridadClienteDto findByIpCliente(String ipCliente);
    SeguridadClienteDto save(SeguridadClienteRequestDto seguridadClienteDto);
}
