package gob.pe.essalud.client.service;


import gob.pe.essalud.client.dto.FichaDto;
import gob.pe.essalud.client.dto.PacienteDto;
import gob.pe.essalud.client.dto.usuario.UsuarioPerfilDto;

public interface PerfilService {
    PacienteDto save(UsuarioPerfilDto model);

    FichaDto saveFicha(FichaDto model);

    UsuarioPerfilDto get(String tipoDocumento, String numeroDocumento);
}
