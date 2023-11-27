package gob.pe.essalud.emailservice.common.email;

import gob.pe.essalud.emailservice.common.Enum.MiConsultaEmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class MiConsultaEmailContentBuilder {

    private static final String FRAGMENT = "fragment";

    private static final String TEMPLATE = "email/miconsulta/template";
    private static final String FRAGMENTS_PATH = "email/miconsulta/fragments/";

    private final TemplateEngine templateEngine;
    private final Environment env;

    @Autowired
    public MiConsultaEmailContentBuilder(
            TemplateEngine templateEngine,
            Environment env
    ) {
        this.templateEngine = templateEngine;
        this.env = env;
    }

    private Context getContext(MiConsultaEmailTemplate emailTemplate) {
        Context context = new Context();
        String fragment = FRAGMENTS_PATH + emailTemplate.value;
        context.setVariable(FRAGMENT, fragment);
        return context;
    }

    private String build(Context context) {
        context.setVariable("applicationLogo", env.getProperty("email-template.miconsulta.application-logo-url"));
        context.setVariable("essaludLogo", env.getProperty("email-template.miconsulta.essalud-logo-url"));
        return templateEngine.process(TEMPLATE, context);
    }

    public String registrationCode(String code) {
        Context context = getContext(MiConsultaEmailTemplate.REGISTRATION_CODE);
        context.setVariable("code", code);
        return build(context);
    }

    public String resetPassword(String url) {
        Context context = getContext(MiConsultaEmailTemplate.RESET_PASSWORD);
        context.setVariable("url", url);
        return build(context);
    }

    public String resetPasswordApk(String code) {
        Context context = getContext(MiConsultaEmailTemplate.RESET_PASSWORD_APK);
        context.setVariable("code", code);
        return build(context);
    }

    /*public String padomiInscription() {
        Context context = getContext(EmailTemplate.PADOMI_INSCRIPTION);
        return build(context);
    }*/
}