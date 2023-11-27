package gob.pe.essalud.client.dto.asegurado;

import gob.pe.essalud.client.base.BaseDto;
import gob.pe.essalud.client.dto.CondicionSaludDto;
import gob.pe.essalud.client.dto.ContactoDto;
import gob.pe.essalud.client.dto.DireccionDto;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AseguradoRequestDto extends BaseDto {
    private Integer idAsegurado;
    private String tipoDocIdent;
    private String numeroDocIdent;
    private String nombres;
    private String primerNombre;
    private String segundoNombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private int edad;
    private String fechaNacimiento;
    private String codSexo;
    private String codCentro;
    private String userLogin;
    private boolean indPadomi;
    private ContactoDto contacto = new ContactoDto();
    private DireccionDto direccion = new DireccionDto();
    private CondicionHogarDto condicionHogar = new CondicionHogarDto();
    private List<CondicionSaludDto> listCondicionSalud;
    private List<Map> listFamiliar;
}
