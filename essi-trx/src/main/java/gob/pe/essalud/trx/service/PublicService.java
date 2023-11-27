package gob.pe.essalud.trx.service;

import gob.pe.essalud.trx.dto.bi.ConsultaCentroLatLongDto;
import gob.pe.essalud.trx.dto.publics.IpressCitaDto;
import gob.pe.essalud.trx.dto.publics.IpressSinCitaDto;
import gob.pe.essalud.trx.dto.publics.RedDto;
import gob.pe.essalud.trx.dto.publics.ServicioIpressDto;

import java.util.List;

public interface PublicService {

    ConsultaCentroLatLongDto consultaCentroLatLong(String cenAsiCod);

    List<IpressCitaDto> getListaIpressCita();

    List<ServicioIpressDto> getListaServiciosIpress(int idIpress);

    List<RedDto> getListaRedes();

    List<IpressSinCitaDto> getListaIpressSinCita(String red);

}
