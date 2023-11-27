package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class CentroDto extends BaseDto {
    private String oriCenAsi;
    private String cenAsiCod;
    private String descripcion;
    private String codRed;
    private boolean indPadomi;
    private boolean indPedirCita;
    private boolean indTeleUrgencia;
}
