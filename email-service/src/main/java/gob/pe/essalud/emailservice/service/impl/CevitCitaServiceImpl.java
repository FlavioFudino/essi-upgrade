package gob.pe.essalud.emailservice.service.impl;

import gob.pe.essalud.emailservice.common.email.CevitCitasEmailContentBuilder;
import gob.pe.essalud.emailservice.common.email.EmailSender;
import gob.pe.essalud.emailservice.dto.cevitCitas.RegistrarCevitCitaDto;
import gob.pe.essalud.emailservice.service.CevitCitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CevitCitaServiceImpl implements CevitCitaService {

    private final EmailSender emailSender;

    private final CevitCitasEmailContentBuilder mailContentBuilder;

    @Override
    public boolean registrarCita(RegistrarCevitCitaDto requestDto) {
        String message = mailContentBuilder.registrationInfo(requestDto);
        return emailSender.send(requestDto.getEmail(),"EsSalud - Cita Registrada con exito!",message);
    }
}
