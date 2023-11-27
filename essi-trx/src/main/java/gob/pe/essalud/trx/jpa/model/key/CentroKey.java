package gob.pe.essalud.trx.jpa.model.key;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CentroKey implements Serializable {

    private String oriCenAsi;
    private String cenAsiCod;

}
