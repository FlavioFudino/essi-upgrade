package gob.pe.essalud.client.dto.essi;

import gob.pe.essalud.client.base.BaseDto;
import gob.pe.essalud.client.common.constants.DateFormat;
import gob.pe.essalud.client.common.util.DateUtil;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class EssiPacienteResponseDto extends BaseDto {
    private String apeMaterno;
    private String apePaterno;
    private String codCentro;
    private String codTipoDoc;
    private String desCentro;
    private String email;
    private String fecNac;
    private String numCelular;
    private String numDoc;
    private String numTelefono;
    private String priNombre;
    private String segNombre;
    private String fecVigHasta;
    private boolean indicadorCita;
    private boolean indPedirCita;
    private String nombreCompleto;
    private boolean indCam;
    private int tipoAlerta = 0;
    private String genero;
    private boolean indTeleUrgencia;

    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder();

        if (priNombre != null)
            sb.append(priNombre.trim());

        if (segNombre != null)
            sb.append(" ").append(segNombre.trim());

        if (apePaterno != null)
            sb.append(" ").append(apePaterno.trim());

        if (apeMaterno != null)
            sb.append(" ").append(apeMaterno.trim());

        return sb.toString();
    }

    private int getEdad() {
        return DateUtil.calculateAge(fecNac);
    }

    public boolean isCam() {
        try {
            Date fechaHoy = DateUtil.currentDateLima();
            Date vigHasta = DateUtil.stringToDate(fecVigHasta, DateFormat.DD_MM_YYYY);

            long diff = DateUtil.dateDiffInDays(fechaHoy, vigHasta);
            boolean isVigActiva = (diff >= 0);
            boolean isMayor60 = (getEdad() > 60);
            return (isVigActiva && isMayor60);
        }
        catch (Exception ex) {
            return false;
        }
    }
}
