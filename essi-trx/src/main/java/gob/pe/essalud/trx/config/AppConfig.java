package gob.pe.essalud.trx.config;

import gob.pe.essalud.trx.base.BaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan({"gob.pe.essalud.trx.*"})
@EnableAsync
public class AppConfig extends BaseConfig {

}