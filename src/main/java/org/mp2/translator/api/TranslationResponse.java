package org.mp2.translator.api;

public class TranslationResponse {
    private String sourceText;
    private String sourceLanguage;
    private String targetLanguage;
    private String translatedText;
    private String model;

    public TranslationResponse() {
    }

    public TranslationResponse(String sourceText, String sourceLanguage, String targetLanguage, String translatedText,
            String model) {
        this.sourceText = sourceText;
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        this.translatedText = translatedText;
        this.model = model;
    }

    public String getSourceText() {
        return sourceText;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public String getModel() {
        return model;
    }
}
