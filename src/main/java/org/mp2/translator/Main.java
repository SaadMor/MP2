package org.mp2.translator;

import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.mp2.translator.config.TranslatorApplication;

public class Main {
    public static void main(String[] args) throws Exception {
        String baseUri = System.getenv().getOrDefault("TRANSLATOR_BASE_URI", "http://0.0.0.0:8080/");
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), new TranslatorApplication());
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        System.out.println("Darija Translator running at " + baseUri + "api/translator/translate");
        Thread.currentThread().join();
    }
}
