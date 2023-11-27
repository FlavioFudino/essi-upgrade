package gob.pe.essalud.trx.dto;

import lombok.Data;

@Data
public class PacienteAseguradoRequestDto {
    private String tipoDoc;
    private String numDoc;
    private String fecNac;
}
