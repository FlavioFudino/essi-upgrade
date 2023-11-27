package gob.pe.essalud.client.dto.cam;

import lombok.Data;

@Data
public class ApiCamResponse <T> implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String status;
    private Integer number;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
