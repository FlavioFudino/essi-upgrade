package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.ParametroDto;
import gob.pe.essalud.trx.dto.UbigeoDataDto;
import gob.pe.essalud.trx.dto.UbigeoDto;

import java.util.List;

public interface UbigeoService {
    List<ParametroDto> getDepartments();

    List<ParametroDto> getProvinces(String idDepartamento);

    List<UbigeoDto> getDistricts(String idDepartamento, String idProvincia);

    UbigeoDataDto get(String codigo);
}
