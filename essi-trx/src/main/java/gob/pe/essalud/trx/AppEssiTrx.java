package gob.pe.essalud.trx;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppEssiTrx extends SpringBootServletInitializer implements CommandLineRunner {
    private static final Logger log = LogManager.getLogger(AppEssiTrx.class);

    public static void main(String[] args) {
        SpringApplication.run(AppEssiTrx.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        log.info("init AppEssiTrx...");
    }

}
