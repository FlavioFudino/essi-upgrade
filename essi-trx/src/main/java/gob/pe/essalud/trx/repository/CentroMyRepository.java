package gob.pe.essalud.trx.repository;


import gob.pe.essalud.trx.dto.CentroDto;
import gob.pe.essalud.trx.dto.publics.IpressCitaDto;
import gob.pe.essalud.trx.dto.publics.IpressSinCitaDto;
import gob.pe.essalud.trx.dto.publics.RedDto;
import gob.pe.essalud.trx.dto.publics.ServicioIpressDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CentroMyRepository {
    List<CentroDto> getListCentro(Map param);

    Boolean checkApplyCita(@Param("oriCenAsiCod") String oriCenAsiCod,
                           @Param("cenAsiCod") String cenAsiCod);

    List<IpressCitaDto> getListaIpressCita();

    List<ServicioIpressDto> getListaServiciosIpress(int idIpress);

    List<RedDto> getListaRedes();

    List<IpressSinCitaDto> getListaIpressSinCita(String red);

    CentroDto getCentroData(String codCentro);
}
