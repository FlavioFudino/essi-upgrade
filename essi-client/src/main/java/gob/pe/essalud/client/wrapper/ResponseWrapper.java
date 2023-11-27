package gob.pe.essalud.client.wrapper;

import gob.pe.essalud.client.common.constants.Enumeradores;
import lombok.Data;

import javax.annotation.PostConstruct;

@Data
public class ResponseWrapper {
    Integer status;
    String statusText;
    String message;
    String path;

    @PostConstruct
    public void initIt() {
        this.statusText = Enumeradores.StatusText.OK.name();
        this.status = 200;
    }

    public ResponseWrapper() {
    }

    public ResponseWrapper(String statusText, String message) {
        this.statusText = statusText;
        this.message = message;
    }
}
