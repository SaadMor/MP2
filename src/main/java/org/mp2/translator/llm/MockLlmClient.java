package org.mp2.translator.llm;

public class MockLlmClient implements LlmClient {
    @Override
    public String translate(String text, String sourceLanguage, String targetLanguage) {
        return "[MOCK-DARIJA] " + text;
    }

    @Override
    public String modelName() {
        return "mock-local";
    }
}
