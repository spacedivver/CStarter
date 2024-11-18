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
db_port = os.getenv('DB_PORT')
db_user = os.getenv('DB_USER')
db_password = os.getenv('DB_PASSWORD')
db_name = os.getenv('DB_NAME')

client = OpenAI(api_key=api_key)
print(f"API 키가 로드되었습니다: {api_key is not None}")


# 데이터베이스에서 가장 최근의 답변 가져오기
def get_answer(mno, clno, number):
    try:
        # MySQL 연결
        connection = mysql.connector.connect(
            host=db_host,
            port=int(db_port),
            user=db_user,
            password=db_password,
            database=db_name
        )
        cursor = connection.cursor()
        
        # 조건에 맞는 답변 가져오기
        query = """
        SELECT cq.answer
        FROM cover_letter_question cq
        INNER JOIN cover_letter cl ON cq.clno = cl.clno
        INNER JOIN user u ON cl.mno = u.mno
        WHERE u.mno = %s AND cl.clno = %s AND cq.number = %s
        """
        cursor.execute(query, (mno, clno, number))
        result = cursor.fetchone()
        
        if result:
            return result[0]  # 답변 텍스트 반환
        else:
            print("조건에 맞는 답변이 없습니다.")
            return None
        
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


# 추가 질문 생성 및 TTS 수행
def generate_follow_up_question(answer_text):
    messages = [
        {"role": "system", "content": "너는 면접관이야."},
        {"role": "user", "content": f"지원자의 답변: {answer_text}\n\n위 답변에 대해 추가로 1개의 질문을 만들어줘."}
    ]
    
    response = client.chat.completions.create(
        model="gpt-4o",
        messages=messages,
        max_tokens=100,
        temperature=0.7
    )

    follow_up_question = response.choices[0].message.content.strip()
    print("추가 질문:", follow_up_question)

    # 추가 질문을 JSON 파일로 저장
    with open('furtherQue.json', 'w', encoding='utf-8') as c_file:
        json.dump({"follow_up_question": follow_up_question}, c_file, ensure_ascii=False, indent=4)
    print("furtherQue.json 파일이 성공적으로 생성되었습니다.")

    # TTS 수행
    text_to_speech(follow_up_question)
    playsound.playsound("question.mp3")  # 오디오 재생
    os.remove("question.mp3")  # 오디오 파일 삭제


# 실행
mno = 9999  # 사용자 mno
clno = 9999  # cover_letter clno
number = 1  # cover_letter_question number

# 조건에 맞는 답변 가져오기
latest_answer = get_answer(mno, clno, number)
if latest_answer:
    generate_follow_up_question(latest_answer)
else:
    print("추가 질문을 생성할 답변이 없습니다.")