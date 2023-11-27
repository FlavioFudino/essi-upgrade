package gob.pe.essalud.emailservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AppEmailService extends SpringBootServletInitializer implements CommandLineRunner {
    private static final Logger log = LogManager.getLogger(AppEmailService.class);

    public static void main(String[] args) {
        SpringApplication.run(AppEmailService.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        log.info("init AppEmailService...");
    }

}
