package org.mp2.translator.service;

import org.mp2.translator.api.TranslationResponse;
import org.mp2.translator.llm.GeminiLlmClient;
import org.mp2.translator.llm.LlmClient;
import org.mp2.translator.llm.MockLlmClient;

public class TranslationService {
    private final LlmClient llmClient;

    public TranslationService() {
        String geminiKey = System.getenv("GEMINI_API_KEY");
        this.llmClient = (geminiKey == null || geminiKey.isBlank()) ? new MockLlmClient() : new GeminiLlmClient(geminiKey);
    }

    public TranslationResponse translate(String text, String sourceLanguage, String targetLanguage) {
        String translated = llmClient.translate(text, sourceLanguage, targetLanguage);
        return new TranslationResponse(text, sourceLanguage, targetLanguage, translated, llmClient.modelName());
    }
}
