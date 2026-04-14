<?php
$url = getenv('TRANSLATOR_URL') ?: 'http://localhost:8080/api/translator/translate';
$user = getenv('BASIC_AUTH_USER') ?: 'admin';
$password = getenv('BASIC_AUTH_PASSWORD') ?: 'admin123';

$stdin = fopen("php://stdin", "r");
echo "Enter text to translate: ";
$text = trim(fgets($stdin));

$payload = json_encode([
    'text' => $text,
    'sourceLanguage' => 'English'
]);

$ch = curl_init($url);
curl_setopt_array($ch, [
    CURLOPT_POST => true,
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_HTTPHEADER => [
        'Content-Type: application/json',
        'Authorization: Basic ' . base64_encode($user . ':' . $password)
    ],
    CURLOPT_POSTFIELDS => $payload
]);

$response = curl_exec($ch);
$httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
curl_close($ch);

echo "Status: $httpCode\n";
echo $response . "\n";
