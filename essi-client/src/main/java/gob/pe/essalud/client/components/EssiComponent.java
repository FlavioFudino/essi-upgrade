package gob.pe.essalud.client.components;

import gob.pe.essalud.client.base.BaseService;
import gob.pe.essalud.client.client.essi.EssiClient;
import gob.pe.essalud.client.dto.essi.EssiPacienteRequestDto;
import gob.pe.essalud.client.dto.essi.EssiResponseDto;
import gob.pe.essalud.client.dto.essi.PacienteEssiDto;
import org.springframework.stereotype.Component;

@Component
public class EssiComponent extends BaseService {

    private final EssiClient _essiPacienteClient;

    public EssiComponent(EssiClient essiPacienteClient) {
        _essiPacienteClient = essiPacienteClient;
    }

    public PacienteEssiDto getPacienteEssi(String tipoDoc, String numDoc, String fecNac) {
        PacienteEssiDto paciente = null;

        EssiPacienteRequestDto request = new EssiPacienteRequestDto();
        request.setCodOpcion("1");
        request.setCodTipDoc(tipoDoc);
        request.setNumDoc(numDoc);
        request.setFecNacimiento(fecNac);

        EssiResponseDto<PacienteEssiDto[]> essiResp = _essiPacienteClient.loginMovil(request);
        if (!essiResp.getCodError().equals("0")) {
            String message = "Tip.Doc.: " + tipoDoc + ", Num.Doc.: " + numDoc + ", Fec.Nac.: " + fecNac;
            loggerError("No se encontró datos en ESSI", message);
            return null;
        }

        PacienteEssiDto[] result = essiResp.getvDataItem();
        if (result != null && result.length > 0) {
            paciente = result[0];
        } else {
            String message = "Tip.Doc.: " + tipoDoc + ", Num.Doc.: " + numDoc + ", Fec.Nac.: " + fecNac;
            loggerError("No se encontró datos en ESSI", message);
        }
        return paciente;
    }
}
