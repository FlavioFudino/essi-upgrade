package gob.pe.essalud.client.dto.essi;

import lombok.Data;

@Data
public class EssiMedicResponseDto<T> extends gob.pe.essalud.client.base.BaseDto {
    private String codRpta;
    private String desRpta;
    private T vDataItem;

    // Mantener para la serialización, con lombok se serializa a null
    public T getvDataItem() {
        return vDataItem;
    }

    public void setvDataItem(T vDataItem) {
        this.vDataItem = vDataItem;
    }
}
