package gob.pe.essalud.client.dto.usuario;

import gob.pe.essalud.client.dto.CondicionSaludDto;
import gob.pe.essalud.client.dto.ContactoDto;
import gob.pe.essalud.client.dto.DireccionDto;
import gob.pe.essalud.client.dto.asegurado.CondicionHogarDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class UsuarioPerfilDto extends gob.pe.essalud.client.base.BaseDto {
    private Integer idPersona;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String fechaNacimiento;
    private Boolean indPadomi;
    private Boolean indApplyPadomi;
    private ContactoDto contacto;
    private DireccionDto direccion;
    private List<CondicionSaludDto> listCondicionSalud;
    private CondicionHogarDto condicionHogar;
    private List<Map> listFamiliar;
}