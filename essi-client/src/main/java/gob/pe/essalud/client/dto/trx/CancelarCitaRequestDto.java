package gob.pe.essalud.client.dto.trx;

import lombok.Data;

@Data
public class CancelarCitaRequestDto extends gob.pe.essalud.client.base.BaseDto {
    private String oriCenAsis;
    private String codCentro;
    private String codTipDoc;
    private String numDoc;
    private String numCitaCreada; //citActMedNum
}
