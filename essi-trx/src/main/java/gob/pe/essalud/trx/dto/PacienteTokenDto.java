package gob.pe.essalud.trx.dto;

import gob.pe.essalud.trx.base.BaseDto;
import lombok.Data;

@Data
public class PacienteTokenDto extends BaseDto {
    private int tipo; //1 = android / 2 huawei
    private int origen; //1 = web / 2 = movil
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String codCentro;
    private String token;
}
