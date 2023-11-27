package gob.pe.essalud.client.dto.gestion_atencion;

import lombok.Data;

import java.util.Date;

@Data
public class GaRegistrarColaResponseDto {
    private String codTicket;
    private Date fechaRegistro;
    private String guiidTicket;

    private Integer tipoRegistro;
    private Integer tipoRespuesta;
    private boolean sobreescribir;
}
