package gob.pe.essalud.client.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.reniec.ReniecClient;
import gob.pe.essalud.client.common.constants.ReciecCode;
import gob.pe.essalud.client.dto.reniec.DatosBasicos;
import gob.pe.essalud.client.dto.reniec.Persona;
import gob.pe.essalud.client.dto.reniec.PersonaReniec;
import gob.pe.essalud.client.dto.reniec.PersonaWrapper;
import gob.pe.essalud.client.service.ReniecService;

@Service
public class ReniecServiceImpl implements ReniecService {

    private final ReniecClient reniecClient;
    private static final Logger logger = LogManager.getLogger(BaseService.class);

    @Autowired
    public ReniecServiceImpl(ReniecClient reniecClient) {
        this.reniecClient = reniecClient;
    }

    @Override
    public PersonaReniec getPersona(String numDocumento) {
        PersonaReniec result = null;
        PersonaWrapper personaWrapper = reniecClient.getPerson(numDocumento);
        if (personaWrapper == null) {
            logger.info("SERVICIO RENIEC: " + "Respuesta null");
        }
        boolean isSuccess = personaWrapper != null && personaWrapper.getCodigoError().equals(ReciecCode.SUCCESS);

        if (isSuccess) {
            result = new PersonaReniec();
            Persona persona = personaWrapper.getPersona();
            DatosBasicos datosBasicos = persona.getDatosBasicos();
            result.setApellidoMaterno(datosBasicos.getApellidoMaterno());
            result.setApellidoPaterno(datosBasicos.getApellidoPaterno());
            result.setNombres(datosBasicos.getNombres());

            //DatosNacimiento datosNacimiento = persona.getDatosNacimiento();
            //DatosAdicionales datosAdicionales = persona.getDatosAdicionales();
            //result.setFechaNacimiento(datosNacimiento.getFechaNacimiento());
            //result.setCodSexo(datosAdicionales.getCodigoSexo());
        }
        return result;
    }
}
