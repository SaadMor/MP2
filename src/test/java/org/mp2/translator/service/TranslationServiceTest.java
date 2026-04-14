package org.mp2.translator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mp2.translator.api.TranslationResponse;

class TranslationServiceTest {

    @Test
    void shouldReturnTranslationResponse() {
        TranslationService service = new TranslationService();
        TranslationResponse response = service.translate("Hello", "English", "Darija");

        assertNotNull(response);
        assertEquals("Hello", response.getSourceText());
        assertEquals("English", response.getSourceLanguage());
        assertEquals("Darija", response.getTargetLanguage());
    }
}
