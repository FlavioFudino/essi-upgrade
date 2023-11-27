package gob.pe.essalud.client.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Value("${essiPacienteKey}")
    private String essiPacienteKey;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("ESSIPACIENTE", essiPacienteKey);
    }
}