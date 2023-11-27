package gob.pe.essalud.client.service;


import gob.pe.essalud.client.common.dto.ResponseDto;
import gob.pe.essalud.client.dto.ClaveChangeRequestDto;
import gob.pe.essalud.client.dto.ClaveRecoveryRequestDto;
import gob.pe.essalud.client.dto.ClaveRecoveryResponseDto;

import java.util.Map;

public interface ClaveService {
    ResponseDto<ClaveRecoveryResponseDto> recovery(ClaveRecoveryRequestDto paramInput, String captchaToken,boolean validarCaptcha);

    Map save(ClaveChangeRequestDto paramInput, String captchaToken,boolean validarCaptcha);
}
