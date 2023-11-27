package gob.pe.essalud.client.dto.trx;

import lombok.Data;

import java.util.Date;

@Data
public class CitaDto extends gob.pe.essalud.client.base.BaseDto {
    private String actMedNum;
    private String cenAsiCod;
    private String oriCenAsi;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private Date dateCreate;
    private boolean indConfirmado;
}