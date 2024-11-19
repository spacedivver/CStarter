import sys
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

# 명령줄 인자로 입력값 받기
try:
    clno = int(sys.argv[1])     # 자기소개서 번호
    number = int(sys.argv[2])   # 문항 번호
    rno = int(sys.argv[3])      # 보고서 번호
except IndexError:
    print("input 에러")
    sys.exit(1)
except ValueError:
    print("clno, number 값 에러")
    sys.exit(1)


# 데이터베이스에서 가장 최근의 답변 가져오기
def get_answer(clno, number):
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
        WHERE cl.clno = %s AND cq.number = %s
        """
        cursor.execute(query, (clno, number))
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


# MySQL에 질문 삽입 함수 정의
def insert_questions_to_db(question, clno, number, db_host, db_port, db_user, db_password, db_name):
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
        
        # 질문 삽입
        insert_query = "INSERT INTO cover_letter_question (clno, rno, number, question_type, question) VALUES (%s, %s, %s, 1, %s)"
        cursor.execute(insert_query, (clno, rno, number, question))
        
        # 변경 사항 커밋
        connection.commit()
        print("질문이 데이터베이스에 성공적으로 삽입되었습니다.")
        
    except Exception as e:
        print(f"데이터베이스 삽입 중 오류 발생: {e}")
    finally:
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
def generate_follow_up_question(answer_text, clno, db_host, db_port, db_user, db_password, db_name):
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

    # 질문 삽입 실행
    if follow_up_question:
        insert_questions_to_db(follow_up_question, clno, 2, db_host, db_port, db_user, db_password, db_name)
    else:
        print("유효한 질문이 없어 데이터베이스에 삽입되지 않았습니다.")

    # 추가 질문을 JSON 파일로 저장
    with open('furtherQue.json', 'w', encoding='utf-8') as c_file:
        json.dump({"follow_up_question": follow_up_question}, c_file, ensure_ascii=False, indent=4)
    print("furtherQue.json 파일이 성공적으로 생성되었습니다.")

    # TTS 수행
    text_to_speech(follow_up_question)
    playsound.playsound("question.mp3")  # 오디오 재생
    os.remove("question.mp3")  # 오디오 파일 삭제


# 실행
# 조건에 맞는 답변 가져오기
latest_answer = get_answer(clno, number)

if latest_answer:
    generate_follow_up_question(latest_answer, clno, db_host, db_port, db_user, db_password, db_name)
else:
    print("추가 질문을 생성할 답변이 없습니다.")
