package gob.pe.essalud.client.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.ConsultaCittClient;
import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.components.EssiComponent;
import gob.pe.essalud.client.dto.citt.ConsultaCittItemDto;
import gob.pe.essalud.client.dto.citt.ConsultaCittRespDto;
import gob.pe.essalud.client.dto.citt.PersonaBuscarReqDto;
import gob.pe.essalud.client.service.CittService;

@Service
public class CittServiceImpl extends BaseService implements CittService {

    private final RestTemplate restTemplate;
    private final ConsultaCittClient _consultaCittClient;
    private final EssiComponent _essiComponent;

    public CittServiceImpl(RestTemplate restTemplate,
                           ConsultaCittClient consultaCittClient,
                           EssiComponent essiComponent) {

        this.restTemplate = restTemplate;
        _consultaCittClient = consultaCittClient;
        _essiComponent = essiComponent;
    }   

    @Override
    public ResponseDto<List<ConsultaCittItemDto>> buscar(PersonaBuscarReqDto input) {
        ConsultaCittRespDto buscarCittResp = _consultaCittClient.buscarCitt(input.getTipoDoc(),input.getNumDoc());

        ResponseDto<List<ConsultaCittItemDto>> response = new ResponseDto<>();

        if (buscarCittResp != null) {
            response.setData(buscarCittResp.getData());
        }

        return response;
    }

}
