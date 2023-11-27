package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class EssiListaSolicitudDto extends gob.pe.essalud.client.base.BaseDto {
    private String desEstadoSolicitud;
    private String fechaCita;
    private String fechaSolicitud;
    private String codTipoCitaSol;
    private String desFechaDeseada;
    private String desTipoCitaSol;
    private String desSubActHosSol;
    private String desServicioHospSolicitado;
    private String codEstadoSolicitud;
    private String desActHosSol;
    private String numSolicitud;
    private String desCentroSolCita;
    private String desTurnoDeseado;
    private String codCentroSolCita;

    private boolean puedePedirCita;
}
