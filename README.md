# MP2 - LLM Powered Darija Translator

This repository contains a complete implementation of an LLM-powered RESTful translation system that translates from a source language (default: English) to Moroccan Arabic Dialect (Darija).

## Step-by-step implementation

### Step 1: Backend Maven REST service (Jakarta REST)
- Implemented a Java 17 Maven project using **Jakarta RESTful Web Services (JAX-RS)** with Jersey.
- Added endpoint: `POST /api/translator/translate`.
- Added DTOs for request/response.
- Added service layer and pluggable LLM client abstraction.

### Step 2: LLM integration
- Implemented `GeminiLlmClient` to call Google Gemini (`gemini-1.5-flash`) using API key from env `GEMINI_API_KEY`.
- Added `MockLlmClient` fallback when API key is missing so the project still runs and can be demoed free/offline.

### Step 3: Security (Basic Authentication)
- Secured endpoint using HTTP Basic Authentication through a request filter.
- Credentials via env vars:
  - `BASIC_AUTH_USER` (default `admin`)
  - `BASIC_AUTH_PASSWORD` (default `admin123`)

### Step 4: HTTP testing support
- Added curl examples (see below) and structured API contract.

### Step 5: Chrome extension (Manifest V3 + side panel)
- Added extension under `clients/chrome-extension`.
- Uses context menu on selected text.
- Opens side panel and auto-populates selected text.
- Calls REST endpoint.
- Added extra features:
  - speech-to-text input
  - text-to-speech read aloud output

### Step 6: Additional clients
- Python client (`clients/python/client.py`)
- PHP client (`clients/php/client.php`)
- React Native mobile client (`clients/react-native/App.js`)

### Step 7: Technical architecture
- Added UML diagrams (Class, Sequence, Deployment) in `docs/architecture.md`.

---

## Run backend

```bash
mvn clean test
mvn exec:java
```

Optional environment variables:

```bash
export GEMINI_API_KEY="your_key"
export BASIC_AUTH_USER="admin"
export BASIC_AUTH_PASSWORD="admin123"
```

## API

### Request
`POST /api/translator/translate`

```json
{
  "text": "Where are you going?",
  "sourceLanguage": "English"
}
```

### cURL test

```bash
curl -X POST "http://localhost:8080/api/translator/translate" \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic $(printf 'admin:admin123' | base64)" \
  -d '{"text":"How are you today?","sourceLanguage":"English"}'
```

---

## Clients

### Python
```bash
pip install requests
python clients/python/client.py
```

### PHP
```bash
php clients/php/client.php
```

### React Native
1. Create RN app or use existing app.
2. Replace `App.js` with `clients/react-native/App.js`.
3. Update `API_URL` with your backend host.

### Chrome extension
1. Open `chrome://extensions`
2. Enable **Developer mode**
3. Click **Load unpacked** and select `clients/chrome-extension`
4. Select text on any page, right click -> **Translate to Darija**

