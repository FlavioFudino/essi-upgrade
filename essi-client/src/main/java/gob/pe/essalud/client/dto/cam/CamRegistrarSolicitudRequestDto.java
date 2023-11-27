package gob.pe.essalud.client.dto.cam;

import lombok.Data;

@Data
public class CamRegistrarSolicitudRequestDto {
    private String direccionActual;
    private String tipoSolicitud;
    private String unidadOperativa;
    private String correo;
    private String ubigeo;
    private String tipoDoc;
    private String celular;
    private String numDoc;
    private String apeMaterno;
    private String telefono;
    private String apePaterno;
    private String nombres;
}
