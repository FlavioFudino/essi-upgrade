package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class RegistrarSolCittReqDto extends gob.pe.essalud.client.base.BaseDto {
    private String solRegTipDoc;
    private String solRegNumDoc;
    private String solRegOriCen;
    private String solRegCenAsis;
    private String solRegAreHos;
    private String solRegActoMed;
    private String solRegCodSer;
    private String solRegFecAten;
    private String solRegTipoDocMed;
    private String solRegNumDocMed;
    private String solRegFecIni;
    private String solRegFecFin;
    private String solRegCodUsu;
    private String solRegCodSis;
}
