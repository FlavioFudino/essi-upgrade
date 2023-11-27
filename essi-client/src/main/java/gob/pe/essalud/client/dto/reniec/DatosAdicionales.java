package gob.pe.essalud.client.dto.reniec;

import lombok.Data;

@Data
public class DatosAdicionales extends gob.pe.essalud.client.base.BaseDto {
    private Object anhoEstudio;
    private Object codigoCaducidad;
    private String codigoEstadoCivil;
    private String codigoGradoInstruccion;
    private String codigoSexo;
    private String constaciaSufragio;
    private Object descripcionCaducidad;
    private Object descripcionConstaciaSufragio;
    private String descripcionDocumentoSustentatorio;
    private String descripcionEstadoCivil;
    private String descripcionGradoInstruccion;
    private String descripcionSexo;
    private String estatura;
    private String fechaExpedicion;
    private String fechaFallecimiento;
    private String fechaInscripcion;
    private String numeroDocumentoSustentatorio;
    private String tipoDocumentoSustentatorio;
}
