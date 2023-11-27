package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class ParametroSolicitudServicioHospDto extends gob.pe.essalud.client.base.BaseDto {
    private String codServicioHosp;
    private String desServicioHosp;
    private ParametroSolicitudActHospDto[] vdataActSubAct;
}
