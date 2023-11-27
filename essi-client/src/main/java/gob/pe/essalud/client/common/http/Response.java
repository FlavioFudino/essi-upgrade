package gob.pe.essalud.client.common.http;

import gob.pe.essalud.client.wrapper.ResponseWrapper;

public class Response<T> extends ResponseWrapper {

    public Response() {
        super.initIt();
    }

    public Response(Integer status, String statusText, String message) {
        this.setStatus(status);
        this.setStatusText(statusText);
        this.setMessage(message);
    }
}
