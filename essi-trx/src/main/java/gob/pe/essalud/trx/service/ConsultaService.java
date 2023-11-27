package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.ConsultaUsuariosDto;

import java.util.List;

public interface ConsultaService {
    List<ConsultaUsuariosDto> consultarUsuarios();
}
