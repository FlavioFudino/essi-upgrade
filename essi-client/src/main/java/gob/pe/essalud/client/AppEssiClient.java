package gob.pe.essalud.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableCaching
public class AppEssiClient extends SpringBootServletInitializer implements CommandLineRunner {
    private static final Logger log = LogManager.getLogger(AppEssiClient.class);

    public static void main(String[] args) {
        SpringApplication.run(AppEssiClient.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        log.info("init AppEssiClient...");
    }

}
