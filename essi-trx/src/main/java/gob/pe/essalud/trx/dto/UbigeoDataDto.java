package gob.pe.essalud.trx.dto;

import lombok.Data;

import java.util.List;

@Data
public class UbigeoDataDto {
    String codigoUbigeo;
    String codigoDepartamento;
    String codigoProvincia;
    List<ParametroDto> provincias;
    List<UbigeoDto> distritos;
}
