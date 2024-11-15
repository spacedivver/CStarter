import json
from openai import OpenAI
import os
from dotenv import load_dotenv

# .env 파일에서 API 키 로드
load_dotenv()
api_key = os.getenv('OPENAI_API_KEY')
client = OpenAI(api_key=api_key)
print(f"API 키가 로드되었습니다: {api_key is not None}")


# TTS
def text_to_speech(text):
    response = client.audio.speech.create(
        model="tts-1",
        voice="alloy",
        input=text
    )
    response.stream_to_file("question.mp3")
    print("질문:", text)



text_to_speech("question.json")
playsound.playsound("question.mp3")
os.remove("question.mp3")