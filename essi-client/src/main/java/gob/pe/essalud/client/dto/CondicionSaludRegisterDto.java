package gob.pe.essalud.client.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CondicionSaludRegisterDto extends gob.pe.essalud.client.base.BaseDto {
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String fechaNacimiento;
    private List<CondicionSaludDto> condicionSaludList = new ArrayList<>();
}
