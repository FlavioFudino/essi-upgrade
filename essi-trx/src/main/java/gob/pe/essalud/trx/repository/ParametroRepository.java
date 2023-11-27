package gob.pe.essalud.trx.repository;

import gob.pe.essalud.trx.dto.ParametroDto;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ParametroRepository {
    Date getFecha();

    List<ParametroDto> getListOperador();

    List<ParametroDto> getListTipoVia();

    List<ParametroDto> getListParentesco();
}
