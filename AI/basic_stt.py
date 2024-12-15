import sys
import json
from openai import OpenAI
import os
from dotenv import load_dotenv
import sounddevice as sd
import numpy as np
from scipy.io.wavfile import write
import time
import mysql.connector

# .env 파일에서 API 키와 DB 정보 로드
load_dotenv()
api_key = os.getenv('OPENAI_API_KEY')
db_host = os.getenv('DB_HOST')
db_port = int(os.getenv('DB_PORT'))
db_user = os.getenv('DB_USER')
db_password = os.getenv('DB_PASSWORD')
db_name = os.getenv('DB_NAME')

client = OpenAI(api_key=api_key)
print(f"API 키가 로드되었습니다: {api_key is not None}")

# 명령줄 인자로 clno와 number 값 받기
try:
    clno = int(sys.argv[1])  # cover_letter clno
    number = int(sys.argv[2])  # cover_letter_question number
except IndexError:
    print("명령줄 인자로 clno와 number 값을 전달해야 합니다.")
    sys.exit(1)
except ValueError:
    print("clno와 number 값은 정수여야 합니다.")
    sys.exit(1)

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

# 데이터베이스에 답변 저장
def save_answer_to_db(answer, clno, number):
    try:
        # MySQL 연결
        connection = mysql.connector.connect(
            host=db_host,
            port=db_port,
            user=db_user,
            password=db_password,
            database=db_name
        )
        cursor = connection.cursor()
        
        # cover_letter_question 테이블의 answer 업데이트
        query = """
        UPDATE cover_letter_question
        SET answer = %s
        WHERE clno = %s AND number = %s AND EXISTS (
            SELECT 1 FROM cover_letter WHERE clno = %s
        )
        """
        cursor.execute(query, (answer, clno, number, clno))
        
        # 변경 사항 커밋
        connection.commit()
        print(f"사용자 답변이 number={number}에 저장 완료.")
        
    except mysql.connector.Error as err:
        print(f"데이터베이스 오류: {err}")
    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()

# STT 로직 시작
audio_data = record_audio()
file_path = "temp_audio.wav"
write(file_path, 16000, audio_data)

user_input = transcribe_audio_to_text(file_path)
print(f"You said: {user_input}")

# JSON 파일로 저장
output_data = {"user_input": user_input}

with open("interview_answer.json", "w", encoding="utf-8") as json_file:
    json.dump(output_data, json_file, ensure_ascii=False, indent=4)

# 답변을 데이터베이스에 저장
save_answer_to_db(user_input, clno, number)

# 임시 파일 삭제
os.remove(file_path)

