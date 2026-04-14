const API_URL = 'http://localhost:8080/api/translator/translate';
const BASIC_USER = 'admin';
const BASIC_PASSWORD = 'admin123';

const input = document.getElementById('input');
const output = document.getElementById('output');

document.getElementById('translateBtn').addEventListener('click', translate);
document.getElementById('speakInput').addEventListener('click', startSpeechRecognition);
document.getElementById('readOutput').addEventListener('click', () => speak(output.textContent));

chrome.storage.local.get(['selectedText'], (result) => {
  if (result.selectedText) {
    input.value = result.selectedText;
    translate();
  }
});

async function translate() {
  const auth = btoa(`${BASIC_USER}:${BASIC_PASSWORD}`);
  const payload = { text: input.value, sourceLanguage: 'English' };

  const response = await fetch(API_URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Basic ${auth}`
    },
    body: JSON.stringify(payload)
  });

  const data = await response.json();
  output.textContent = data.translatedText || data.error || 'No translation.';
}

function startSpeechRecognition() {
  const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
  if (!SpeechRecognition) {
    output.textContent = 'Speech recognition is not supported in this browser.';
    return;
  }

  const recognition = new SpeechRecognition();
  recognition.lang = 'en-US';
  recognition.onresult = (event) => {
    input.value = event.results[0][0].transcript;
    translate();
  };
  recognition.start();
}

function speak(text) {
  if (!text) return;
  const utterance = new SpeechSynthesisUtterance(text);
  utterance.lang = 'ar-MA';
  window.speechSynthesis.speak(utterance);
}
