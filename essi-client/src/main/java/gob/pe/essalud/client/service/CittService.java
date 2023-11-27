package gob.pe.essalud.client.service;

import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.dto.citt.ConsultaCittItemDto;
import gob.pe.essalud.client.dto.citt.PersonaBuscarReqDto;

import java.util.List;

public interface CittService {

    ResponseDto<List<ConsultaCittItemDto>> buscar(PersonaBuscarReqDto input);

}
