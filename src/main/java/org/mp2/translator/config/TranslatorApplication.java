package org.mp2.translator.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.mp2.translator.api.TranslatorResource;
import org.mp2.translator.auth.BasicAuthFilter;

public class TranslatorApplication extends ResourceConfig {
    public TranslatorApplication() {
        register(JacksonFeature.class);
        register(TranslatorResource.class);
        register(BasicAuthFilter.class);
    }
}
