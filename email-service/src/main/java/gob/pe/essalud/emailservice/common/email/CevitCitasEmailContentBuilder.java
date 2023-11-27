package gob.pe.essalud.emailservice.common.email;

import gob.pe.essalud.emailservice.common.Enum.CevitCitasEmailTemplate;
import gob.pe.essalud.emailservice.common.Enum.MiConsultaEmailTemplate;
import gob.pe.essalud.emailservice.dto.cevitCitas.RegistrarCevitCitaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class CevitCitasEmailContentBuilder {

    private static final String FRAGMENT = "fragment";

    private static final String TEMPLATE = "email/cevit-citas/template";
    private static final String FRAGMENTS_PATH = "email/cevit-citas/fragments/";

    private final TemplateEngine templateEngine;
    private final Environment env;

    @Autowired
    public CevitCitasEmailContentBuilder(
            TemplateEngine templateEngine,
            Environment env
    ) {
        this.templateEngine = templateEngine;
        this.env = env;
    }

    private Context getContext(CevitCitasEmailTemplate emailTemplate) {
        Context context = new Context();
        String fragment = FRAGMENTS_PATH + emailTemplate.value;
        context.setVariable(FRAGMENT, fragment);
        return context;
    }

    private String build(Context context) {
        context.setVariable("applicationLogo", env.getProperty("email-template.miconsulta.essalud-logo-url"));
        context.setVariable("essaludLogo", env.getProperty("email-template.miconsulta.essalud-logo-url"));
        return templateEngine.process(TEMPLATE, context);
    }

    public String registrationInfo(RegistrarCevitCitaDto input) {
        Context context = getContext(CevitCitasEmailTemplate.REGISTRATION_INFO);
        context.setVariable("numCita", input.getIdCita());
        context.setVariable("fecha", input.getFecha());
        context.setVariable("turno", input.getTurno());
        return build(context);
    }
}