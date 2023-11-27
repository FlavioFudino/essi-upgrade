package gob.pe.essalud.trx.client;

import gob.pe.essalud.trx.common.constants.Constantes;
import gob.pe.essalud.trx.dto.smsservice.SmsSendResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "smsservice", url = "${" + Constantes.URL_ENDPOINT_SMS_SERVICE + "}")
public interface SmsClient {

    @GetMapping(Constantes.URL_SMS_SEND)
    SmsSendResponseDto send(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                     @RequestParam(Constantes.PARAM_SMS_SEND_MOBILE) String mobile,
                                     @RequestParam(Constantes.PARAM_SMS_SEND_COUNTRY) String country,
                                     @RequestParam(Constantes.PARAM_SMS_SEND_MESSAGE) String message,
                                     @RequestParam(Constantes.PARAM_SMS_SEND_MESSAGE_FORMAT) String messageFormat);

}
