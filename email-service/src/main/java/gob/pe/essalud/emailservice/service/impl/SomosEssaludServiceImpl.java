package gob.pe.essalud.emailservice.service.impl;

import gob.pe.essalud.emailservice.common.email.EmailSender;
import gob.pe.essalud.emailservice.common.email.SomosEssaludEmailContentBuilder;
import gob.pe.essalud.emailservice.dto.common.ActivarCuentaRequestDto;
import gob.pe.essalud.emailservice.dto.common.RecuperarClaveWebRequestDto;
import gob.pe.essalud.emailservice.service.SomosEssaludService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SomosEssaludServiceImpl implements SomosEssaludService {

    private final EmailSender emailSender;
    private final SomosEssaludEmailContentBuilder mailContentBuilder;

    @Override
    public boolean recuperarClave(RecuperarClaveWebRequestDto input) {
        String message = mailContentBuilder.resetPassword(input.getUrl());
        return emailSender.send(input.getEmail(),"Somos-EsSalud: Restablecer Contraseña",message);
    }

    @Override
    public boolean activarCuenta(ActivarCuentaRequestDto input) {
        String message = mailContentBuilder.registrationCode(input.getToken());
        return emailSender.send(input.getEmail(),"Somos-EsSalud: Código de Activación",message);
    }
}
