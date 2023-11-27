package gob.pe.essalud.trx.repository;


import gob.pe.essalud.trx.dto.ParametroDto;
import gob.pe.essalud.trx.dto.UbigeoDataDto;
import gob.pe.essalud.trx.dto.UbigeoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbigeoMyRepository {
    UbigeoDataDto getUbigeo(String codigo);

    List<ParametroDto> getDepartments();

    List<ParametroDto> getProvinces(String idDepartamento);

    List<UbigeoDto> getDistricts(@Param("idDepartamento") String idDepartamento,
                                 @Param("idProvincia") String idProvincia);
}