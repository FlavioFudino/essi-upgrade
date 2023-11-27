package gob.pe.essalud.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gob.pe.essalud.client.dto.asegurado.CondicionHogarDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class FichaDto extends gob.pe.essalud.client.base.BaseDto {
    @JsonIgnore
    private String tipoDocIdent;
    @JsonIgnore
    private String numDocIdent;
    @NotNull
    private CondicionHogarDto CondicionHogar;
    @NotNull
    private DireccionDto Direccion;
    private List<CondicionSaludDto> listCondicionSalud = new ArrayList<>();
}
