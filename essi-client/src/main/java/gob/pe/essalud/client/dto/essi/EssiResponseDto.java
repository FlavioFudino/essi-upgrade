package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class EssiResponseDto<T> implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String codError;
    private String desError;
    private T vDataItem;

    // Mantener para la serialización, con lombok se serializa a null
    public T getvDataItem() {
        return vDataItem;
    }

    public void setvDataItem(T vDataItem) {
        this.vDataItem = vDataItem;
    }
}
