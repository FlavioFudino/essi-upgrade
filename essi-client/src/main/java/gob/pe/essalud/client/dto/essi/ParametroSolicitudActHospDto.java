package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class ParametroSolicitudActHospDto extends gob.pe.essalud.client.base.BaseDto {
    private String codActSubAct;
    private String desActHosp;
    private String desSubActHosp;
}
