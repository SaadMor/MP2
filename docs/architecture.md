# Technical Architecture (UML)

## 1) Class Diagram
```mermaid
classDiagram
    class TranslatorResource {
      +translate(request: TranslationRequest): Response
    }

    class TranslationService {
      +translate(text, sourceLanguage, targetLanguage): TranslationResponse
    }

    class LlmClient {
      <<interface>>
      +translate(text, sourceLanguage, targetLanguage): String
      +modelName(): String
    }

    class GeminiLlmClient
    class MockLlmClient
    class BasicAuthFilter
    class AuthConfig

    TranslatorResource --> TranslationService
    TranslationService --> LlmClient
    LlmClient <|.. GeminiLlmClient
    LlmClient <|.. MockLlmClient
    TranslatorResource ..> BasicAuthFilter
    BasicAuthFilter --> AuthConfig
```

## 2) Sequence Diagram (Chrome Extension to backend)
```mermaid
sequenceDiagram
    participant U as User
    participant CE as Chrome Extension
    participant API as TranslatorResource
    participant S as TranslationService
    participant LLM as Gemini/Mock LLM

    U->>CE: Select text + right click menu
    CE->>CE: Open side panel + auto-fill selected text
    CE->>API: POST /api/translator/translate (Basic Auth)
    API->>S: translate(text, source, Darija)
    S->>LLM: prompt translation request
    LLM-->>S: translated text
    S-->>API: TranslationResponse
    API-->>CE: JSON translatedText
    CE-->>U: Display/Read aloud result
```

## 3) Deployment Diagram
```mermaid
flowchart LR
    subgraph Client_Side
      Browser[Chrome + Extension]
      Python[Python Client]
      PHP[PHP Client]
      Mobile[React Native App]
    end

    subgraph Server_Side
      REST[Jakarta REST Service\n(Grizzly/Jersey)]
      Auth[Basic Auth Filter]
      LLMClient[GeminiLlmClient / MockLlmClient]
    end

    Browser --> REST
    Python --> REST
    PHP --> REST
    Mobile --> REST

    REST --> Auth
    REST --> LLMClient
    LLMClient --> Gemini[(Google Gemini API)]
```
