import json
from openai import OpenAI
import os
from dotenv import load_dotenv
import mysql.connector
import playsound

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

# 데이터베이스에서 질문 가져오기
def get_question_by_order(current_number):
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
        
        # 번호 순서대로 가져오기
        query = "SELECT question FROM cover_letter_question WHERE number = %s"
        cursor.execute(query, (current_number,))
        result = cursor.fetchone()
        
        if result:
            return result[0]
        else:
            return None  # 더 이상 질문이 없으면 None 반환
        
    except mysql.connector.Error as err:
        print(f"데이터베이스 오류: {err}")
        return None
    finally:
        if connection.is_connected():
            cursor.close()
            connection.close()

# TTS
def text_to_speech(text):
    print("질문:", text)
    response = client.audio.speech.create(
        model="tts-1",
        voice="alloy",
        input=text
    )
    response.stream_to_file("question.mp3")

# 실행
current_number = 1  # 첫 번째 질문부터 시작
while True:
    question = get_question_by_order(current_number)  # 번호 순서대로 질문 가져오기
    if question:
        text_to_speech(question)  # TTS로 질문 읽기
        playsound.playsound("question.mp3")  # 오디오 재생
        os.remove("question.mp3")  # 오디오 파일 삭제
        current_number += 1  # 다음 질문 번호로 이동
    else:
        print("모든 질문이 출력되었습니다.")
        break
