package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.CitaDto;
import gob.pe.essalud.trx.dto.UsuarioDataDto;

import java.util.List;

public interface CitaService {

    CitaDto save(CitaDto citaDto);

    List<CitaDto> buscar(UsuarioDataDto paramInput);

}
