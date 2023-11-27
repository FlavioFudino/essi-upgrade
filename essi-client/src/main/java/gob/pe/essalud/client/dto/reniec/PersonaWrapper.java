package gob.pe.essalud.client.dto.reniec;

import lombok.Data;

@Data
public class PersonaWrapper extends gob.pe.essalud.client.base.BaseDto {
    private String codigoError;
    private String mensajeError;
    private String firmaBase64;
    private String fotoBase64;
    private String longitudFirma;
    private String longitudFoto;
    private Object huellaDerechaBase64;
    private Object huellaIzquierdaBase64;
    private String longitudHuellaDerecha;
    private String longitudHuellaIzquierda;
    private String mostrarDatos;
    private Persona persona;
    private Object subtipoConsulta;
}
