package gob.pe.essalud.trx.repository;


import gob.pe.essalud.trx.dto.RequestGenericDto;
import gob.pe.essalud.trx.dto.UsuarioOauthDto;
import gob.pe.essalud.trx.dto.UsuarioPerfilDto;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioMyRepository {
    UsuarioOauthDto getByUsername(String username);

    UsuarioPerfilDto getPerfil(RequestGenericDto request);
}