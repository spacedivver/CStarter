import json
from openai import OpenAI
import os
from dotenv import load_dotenv

# .env 파일에서 API 키 로드
load_dotenv()
api_key = os.getenv('OPENAI_API_KEY')
client = OpenAI(api_key=api_key)
print(f"API 키가 로드되었습니다: {api_key is not None}")



# STT를 위한 음성 녹음 및 파일 저장
def record_audio(duration=10, fs=16000):
    print("Recording", end="", flush=True)
    audio_data = sd.rec(int(duration * fs), samplerate=fs, channels=1, dtype='int16')
    
    for _ in range(duration):
        print(".", end="", flush=True)
        time.sleep(1)
    
    sd.wait()  # Wait until recording is finished
    print("\nRecording finished.")
    return audio_data

# STT
def transcribe_audio_to_text(file_path):
    with open(file_path, 'rb') as audio_file:
        response = client.audio.transcriptions.create(
            model="whisper-1",
            file=audio_file,
        )
    return response.text  # Transcription 객체에서 텍스트 추출


# stt 로직 시작

audio_data = record_audio()
file_path = "temp_audio.wav"
write(file_path, 16000, audio_data)

user_input = transcribe_audio_to_text(file_path)
print(f"You said: {user_input}")

# JSON 파일로 저장
output_data = {"user_input": user_input}


with open("answer_text.json", "w", encoding="utf-8") as json_file:
    json.dump(output_data, json_file, ensure_ascii=False, indent=4)