package gob.pe.essalud.trx.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CitaDto {
    private String actMedNum;
    private String cenAsiCod;
    private String oriCenAsi;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private Date dateCreate;
    private boolean indConfirmado;
}
