package edu.bbte.idde.gvim2021.utilities;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class HandlebarsTemplateFactory {
    private static Handlebars handlebars;

    public static synchronized Template getTemplate(String templateName) throws IOException {
        // lazy initialization
        if (handlebars == null) {
            log.info("Building handlebars renderer");

            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".hbs");
            handlebars = new Handlebars(loader);
        }

        return handlebars.compile(templateName);
    }
}
