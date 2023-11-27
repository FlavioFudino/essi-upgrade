package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class ParametroSolicitudResponseDto extends gob.pe.essalud.client.base.BaseDto {
    private ParametroSolicitudFechaDeseadaDto[] dataParmFechaDeseada;
    private ParametroSolicitudServicioHospDto[] dataParmServicioHosp;
    private ParametroSolicitudTurnoDeseadoDto[] dataParmTurnoDeseado;
}
