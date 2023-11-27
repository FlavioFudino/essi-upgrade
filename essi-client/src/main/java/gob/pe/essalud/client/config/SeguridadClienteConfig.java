package gob.pe.essalud.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "seguridad-cliente")
@Getter
@Setter
public class SeguridadClienteConfig {
    private boolean enabled;
    private int maxAttempts;
    private int blockTimeInMinutes;
    private int detalleExpireInHours;
}
