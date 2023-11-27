package gob.pe.essalud.trx.repository;

import gob.pe.essalud.trx.dto.ConsultaUsuariosDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaMyRepository {
    List<ConsultaUsuariosDto> getConsultaUsuarios();
}
