package gob.pe.essalud.client.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

public class PropertiesUtil {
    private static final Logger logger = LogManager.getLogger(PropertiesUtil.class);
    private Environment env;

    public PropertiesUtil(Environment env) {
        this.env = env;
    }

    public String getPropertiesString(String key) {
        return getValueEncoding(env.getProperty(key));
    }

    private String getValueEncoding(String value) {
        String val = "";
        try {
            if (StringUtils.isEmpty(value)) {
                throw new UnsupportedEncodingException("error al obtener key properties...valor nulo");
            }
            return new String(value.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info(e);
        }
        return val;
    }
}
