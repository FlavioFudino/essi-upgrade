package gob.pe.essalud.trx.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.essalud.trx.base.BaseService;
import gob.pe.essalud.trx.dto.ParametroDto;
import gob.pe.essalud.trx.dto.UbigeoDataDto;
import gob.pe.essalud.trx.dto.UbigeoDto;
import gob.pe.essalud.trx.repository.UbigeoMyRepository;
import gob.pe.essalud.trx.service.UbigeoService;

@Service
public class UbigeoServiceImpl extends BaseService implements UbigeoService {
    private final UbigeoMyRepository ubigeoMyRepository;

    @Autowired
    public UbigeoServiceImpl(UbigeoMyRepository ubigeoMyRepository) {
        this.ubigeoMyRepository = ubigeoMyRepository;
    }

    @Override
    public List<ParametroDto> getDepartments() {
        return ubigeoMyRepository.getDepartments();
    }

    @Override
    public List<ParametroDto> getProvinces(String idDepartamento) {
        return ubigeoMyRepository.getProvinces(idDepartamento);
    }

    @Override
    public List<UbigeoDto> getDistricts(String idDepartamento, String idProvincia) {
        return ubigeoMyRepository.getDistricts(idDepartamento, idProvincia);
    }

    @Override
    public UbigeoDataDto get(String codigo) {
        UbigeoDataDto data = ubigeoMyRepository.getUbigeo(codigo);
        List<ParametroDto> provinces = ubigeoMyRepository.getProvinces(data.getCodigoDepartamento());
        List<UbigeoDto> districts = ubigeoMyRepository
                .getDistricts(data.getCodigoDepartamento(), data.getCodigoProvincia());
        data.setProvincias(provinces);
        data.setDistritos(districts);
        return data;
    }
}
