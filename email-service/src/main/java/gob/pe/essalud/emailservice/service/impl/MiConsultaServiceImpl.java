package gob.pe.essalud.emailservice.service.impl;

import gob.pe.essalud.emailservice.common.email.MiConsultaEmailContentBuilder;
import gob.pe.essalud.emailservice.common.email.EmailSender;
import gob.pe.essalud.emailservice.dto.miconsulta.RecuperarClaveMobileRequestDto;
import gob.pe.essalud.emailservice.dto.common.RecuperarClaveWebRequestDto;
import gob.pe.essalud.emailservice.dto.miconsulta.RegistrarUsuarioRequestDto;
import gob.pe.essalud.emailservice.service.MiConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MiConsultaServiceImpl implements MiConsultaService {

    private final EmailSender emailSender;
    private final MiConsultaEmailContentBuilder mailContentBuilder;

    @Override
    public boolean registrarUsuario(RegistrarUsuarioRequestDto requestDto) {
        String message = mailContentBuilder.registrationCode(requestDto.getToken());
        return emailSender.send(requestDto.getEmail(),"Bienvenido(a) a EsSalud",message);
    }

    @Override
    public boolean recuperarClaveWeb(RecuperarClaveWebRequestDto requestDto) {
        String message = mailContentBuilder.resetPassword(requestDto.getUrl());
        return emailSender.send(requestDto.getEmail(),"Restablecer Contraseña",message);
    }

    @Override
    public boolean recuperarClaveMobile(RecuperarClaveMobileRequestDto requestDto) {
        String message = mailContentBuilder.resetPasswordApk(requestDto.getToken());
        return emailSender.send(requestDto.getEmail(),"Restablecer Contraseña",message);
    }

}
