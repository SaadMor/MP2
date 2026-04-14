import React, {useState} from 'react';
import {Button, SafeAreaView, Text, TextInput, View} from 'react-native';

const API_URL = 'http://YOUR_SERVER_IP:8080/api/translator/translate';
const BASIC_USER = 'admin';
const BASIC_PASSWORD = 'admin123';

export default function App() {
  const [text, setText] = useState('Hello, how are you?');
  const [result, setResult] = useState('');

  const translate = async () => {
    const auth = btoa(`${BASIC_USER}:${BASIC_PASSWORD}`);
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Basic ${auth}`,
      },
      body: JSON.stringify({text, sourceLanguage: 'English'}),
    });

    const data = await response.json();
    setResult(data.translatedText || data.error || 'No response');
  };

  return (
    <SafeAreaView style={{padding: 16}}>
      <Text style={{fontSize: 20, marginBottom: 8}}>Darija Translator</Text>
      <TextInput
        value={text}
        onChangeText={setText}
        multiline
        style={{borderColor: '#aaa', borderWidth: 1, minHeight: 80, marginBottom: 12, padding: 8}}
      />
      <Button title="Translate" onPress={translate} />
      <View style={{marginTop: 16}}>
        <Text>{result}</Text>
      </View>
    </SafeAreaView>
  );
}
