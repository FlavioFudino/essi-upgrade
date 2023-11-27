package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.SeguridadClienteDetalleRequestDto;
import gob.pe.essalud.trx.dto.SeguridadClienteDetalleResponseDto;

public interface SeguridadClienteDetalleService {
    SeguridadClienteDetalleResponseDto save(SeguridadClienteDetalleRequestDto seguridadClienteDetalleRequestDto);
}
