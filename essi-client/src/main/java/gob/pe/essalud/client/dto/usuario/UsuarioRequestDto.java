package gob.pe.essalud.client.dto.usuario;

import gob.pe.essalud.client.base.BaseDto;
import gob.pe.essalud.client.dto.essi.EssiPacienteResponseDto;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

@Data
@ToString
public class UsuarioRequestDto extends BaseDto {
    private EssiPacienteResponseDto paciente;
    private Map credenciales;
    private Boolean successLogin = false;
}
