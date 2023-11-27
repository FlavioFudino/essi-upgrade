package gob.pe.essalud.emailservice.dto.cevitCitas;

import lombok.Data;

@Data
public class RegistrarCevitCitaDto {
    private String email;
    private String idCita;
    private String fecha;
    private String turno;
}
