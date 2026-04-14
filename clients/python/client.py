import base64
import json
import os
import requests

BASE_URL = os.getenv("TRANSLATOR_URL", "http://localhost:8080/api/translator/translate")
USER = os.getenv("BASIC_AUTH_USER", "admin")
PASSWORD = os.getenv("BASIC_AUTH_PASSWORD", "admin123")

text = input("Enter text to translate: ")
source_language = input("Source language [English]: ") or "English"

auth_value = base64.b64encode(f"{USER}:{PASSWORD}".encode()).decode()
headers = {
    "Content-Type": "application/json",
    "Authorization": f"Basic {auth_value}"
}
payload = {"text": text, "sourceLanguage": source_language}

resp = requests.post(BASE_URL, headers=headers, data=json.dumps(payload), timeout=30)
print("Status:", resp.status_code)
print(json.dumps(resp.json(), indent=2, ensure_ascii=False))
