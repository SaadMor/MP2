package org.mp2.translator.llm;

public interface LlmClient {
    String translate(String text, String sourceLanguage, String targetLanguage);

    String modelName();
}
