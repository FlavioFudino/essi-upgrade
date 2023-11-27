package gob.pe.essalud.trx.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import gob.pe.essalud.trx.dto.bi.ConsultaCentroLatLongDto;
import gob.pe.essalud.trx.dto.publics.IpressCitaDto;
import gob.pe.essalud.trx.dto.publics.IpressSinCitaDto;
import gob.pe.essalud.trx.dto.publics.RedDto;
import gob.pe.essalud.trx.dto.publics.ServicioIpressDto;
import gob.pe.essalud.trx.jpa.model.CentroModel;
import gob.pe.essalud.trx.jpa.repository.CentroRepository;
import gob.pe.essalud.trx.repository.CentroMyRepository;
import gob.pe.essalud.trx.service.PublicService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicServiceImpl implements PublicService {

    private final CentroRepository centroRepository;
    private final CentroMyRepository centroMyRepository;

    @Override
    public ConsultaCentroLatLongDto consultaCentroLatLong(String cenAsiCod) {

        CentroModel model = centroRepository.findFirstByCenAsiCod(cenAsiCod);

        ConsultaCentroLatLongDto response = new ConsultaCentroLatLongDto();

        BeanUtils.copyProperties(model, response);

        return response;
    }

    @Override
    public List<IpressCitaDto> getListaIpressCita() {
        return centroMyRepository.getListaIpressCita();
    }

    @Override
    public List<ServicioIpressDto> getListaServiciosIpress(int idIpress) {
        return centroMyRepository.getListaServiciosIpress(idIpress);
    }

    @Override
    public List<RedDto> getListaRedes() {
        return centroMyRepository.getListaRedes();
    }

    @Override
    public List<IpressSinCitaDto> getListaIpressSinCita(String red) {
        return centroMyRepository.getListaIpressSinCita(red);
    }
}
