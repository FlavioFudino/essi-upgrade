package gob.pe.essalud.client.dto.reniec;

import lombok.Data;

@Data
public class DatosNacimiento extends gob.pe.essalud.client.base.BaseDto {
    private String apellidoMaternoCasadaMadre;
    private String apellidoMaternoMadre;
    private String apellidoMaternoPadre;
    private String apellidoPaternoMadre;
    private String apellidoPaternoPadre;
    private String codigoUbigeoContinenteNacimiento;
    private String codigoUbigeoDepartamento;
    private String codigoUbigeoDistrito;
    private String codigoUbigeoLocalidad;
    private String codigoUbigeoPaisNacimiento;
    private String codigoUbigeoProvincia;
    private String continenteNacimiento;
    private String departamento;
    private String descripcionTipoDocumento;
    private String distrito;
    private String fechaNacimiento;
    private String localidad;
    private String nombreMadre;
    private String nombrePadre;
    private String numDocumentoMadre;
    private String numDocumentoPadre;
    private String paisNacimiento;
    private String provincia;
    private String tipoDocumentoMadre;
    private String tipoDocumentoPadre;
}
