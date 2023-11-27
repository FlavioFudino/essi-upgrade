package gob.pe.essalud.client.dto.essi_consulta;

import lombok.Data;

@Data
public class ListaSolicitudCittReqDto extends gob.pe.essalud.client.base.BaseDto {
    private String lisTipDoc;
    private String lisNumDoc;
}
